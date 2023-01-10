package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.entity.*;
import com.cydeo.javahedgehogsproject.dto.*;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService, @Lazy InvoiceService invoiceService, ProductService productService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Override
    public List<InvoiceProductDto> findAllById(Long id) {
        CompanyDto loggedInCompany = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(loggedInCompany, new Company());

        return invoiceProductRepository.findAllByInvoice_CompanyAndInvoiceId(company, id).stream()
                .filter(invoiceProduct -> !invoiceProduct.isDeleted())
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .peek(invoiceProductDto -> invoiceProductDto.setTotal(calculate(invoiceProductDto)))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDto> findAllInvoiceProducts(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        List<InvoiceProductDto> DtoList = invoiceProductList.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto())).collect(Collectors.toList());
        for (InvoiceProductDto each : DtoList) {
            BigDecimal x = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice());
            BigDecimal y = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()).multiply(each.getTax()).divide(BigDecimal.valueOf(100));
            each.setTotal(x.add(y).setScale(2, RoundingMode.CEILING));

        }
        return DtoList;
    }

    @Override
    public void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto) {
        //go to DB find that invoice:
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        //find all invoiceProducts belongs to that invoice:
        List<InvoiceProduct> listInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoice.getId());
        //delete one by one all invoiceProducts that we found base on the id:
        listInvoiceProducts.forEach(invoiceProduct -> {
            Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProduct.getId());
            if (foundInvoiceProduct.isPresent()) {
                foundInvoiceProduct.get().setDeleted(true);
                invoiceProductRepository.save(foundInvoiceProduct.get());
            }
        });
    }

    @Override
    public BigDecimal totalTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalTax = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalTax = totalTax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getTax()).divide(BigDecimal.valueOf(100)).multiply(each.getPrice()));
        }
        return totalTax.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal totalPriceWithoutTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalPrice = totalPrice.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));
        }
        return totalPrice.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public void savePurchaseProductByInvoiceId(InvoiceProductDto invoiceProduct, Long id) {
        InvoiceDto invoice = invoiceService.findById(id);
        InvoiceProduct invProduct = mapperUtil.convert(invoiceProduct, new InvoiceProduct());
        invProduct.setProfitLoss(new BigDecimal(0));
        invProduct.setInvoice(mapperUtil.convert(invoice, new Invoice()));
        invoice.setPrice(invoiceProduct.getPrice());
        BigDecimal tax = invoiceProduct.getPrice().multiply(invoiceProduct.getTax());
        invoice.setTax(tax);
        invoice.setTotal(invoiceProduct.getTotal());
        invoiceProductRepository.save(invProduct);
    }

    @Override
    public void deletePurchaseProduct(Long productId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(productId).get();
        invoiceProduct.setDeleted(true);
        invoiceProduct.setPrice(new BigDecimal(0));
        invoiceProduct.setTax(new BigDecimal(0));

        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void approvePurchaseInvoice(Long purchaseInvoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(purchaseInvoiceId);
        if (invoiceProducts.size() > 0) {
            for (InvoiceProduct eachInvoiceProduct : invoiceProducts) {
                Product product = eachInvoiceProduct.getProduct();
                product.setQuantityInStock(product.getQuantityInStock() + eachInvoiceProduct.getQuantity());
                eachInvoiceProduct.setRemainingQty(eachInvoiceProduct.getQuantity()); // it's needed to calculate profit/loss
                InvoiceProductDto invoiceProductDto = mapperUtil.convert(eachInvoiceProduct, new InvoiceProductDto());

                invoiceProductRepository.save(mapperUtil.convert(invoiceProductDto, new InvoiceProduct()));
            }
        } else {
            throw new RuntimeException("Purchase invoice has no products.");
        }
    }

    @Override
    public void saveProduct(InvoiceProductDto invoiceProductDto, Long id) {
        InvoiceDto invoiceDto = invoiceService.findById(id);
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        invoiceProduct.setProfitLoss(new BigDecimal(0));
        invoiceProduct.setInvoice(mapperUtil.convert(invoiceDto, new Invoice()));
        invoiceDto.setInvoiceProducts(List.of(invoiceProductDto));
        invoiceDto.setPrice(invoiceProductDto.getPrice());
        BigDecimal tax = invoiceProductDto.getPrice().multiply(invoiceProductDto.getTax());
        invoiceDto.setTax(tax);
        invoiceDto.setTotal(invoiceProductDto.getTotal());
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void deleteSalesInvoiceProduct(Long invoiceProductId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();
        invoiceProduct.setDeleted(true);
        invoiceProduct.setPrice(new BigDecimal(0));
        invoiceProduct.setTax(new BigDecimal(0));
        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public boolean checkQuantityAmount(Long invoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        List<InvoiceProductDto> invoiceProductDtos = invoiceProducts.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());

        for (InvoiceProductDto each : invoiceProductDtos) {
            ProductDto productDto = productService.findById(each.getProduct().getId());

            if (productDto.getQuantityInStock() < each.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void calculateProfitLossForSale(Long invoiceId) {
        Company currentCompany = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());

        List<InvoiceProduct> salesInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);

        BigDecimal profitLoss = BigDecimal.ZERO;
        for (InvoiceProduct sold : salesInvoiceProducts) { // each sales InvoiceProduct
            sold.setRemainingQty(sold.getQuantity());

            // calculate total price with tax for each sales InvoiceProduct
            BigDecimal salesTotalPrice = sold.getPrice()
                    .multiply(BigDecimal.valueOf(sold.getQuantity()))
                    .multiply(sold.getTax())
                    .divide(BigDecimal.valueOf(100))
                    .add(sold.getPrice()
                            .multiply(BigDecimal.valueOf(sold.getQuantity())));

            Product product = sold.getProduct(); // get the product from sale InvoiceProduct
            product.setQuantityInStock(product.getQuantityInStock() - sold.getQuantity()); // decreasing product quantity

            // get the oldest approved purchase InvoiceProducts based on product_id
            List<InvoiceProduct> purchaseInvoiceProducts =
                    invoiceProductRepository.findAllByInvoice_InvoiceStatusAndInvoice_InvoiceTypeAndInvoice_CompanyAndProduct_IdOrderByInvoice_InvoiceNoAsc(
                            InvoiceStatus.APPROVED, InvoiceType.PURCHASE, currentCompany, product.getId());

            BigDecimal purchaseTotalPrice = BigDecimal.ZERO;

            for (InvoiceProduct purchased : purchaseInvoiceProducts) { // each purchase InvoiceProduct
                // check the product matches
                if (purchased.getProduct().getId().equals(sold.getProduct().getId()) && sold.getRemainingQty() > 0) {

                    // if there is no InvoiceProduct left in purchase invoice to calculate, go to other purchase invoice
                    if (purchased.getRemainingQty() == 0) {
                        continue;
                    }

                    if (purchased.getRemainingQty() > sold.getRemainingQty()) { // calculate the price based on sales quantity
                        purchaseTotalPrice = purchaseTotalPrice
                                .add(purchased.getPrice()
                                        .multiply(BigDecimal.valueOf(sold.getRemainingQty()))
                                        .multiply(purchased.getTax())
                                        .divide(BigDecimal.valueOf(100))
                                        .add(purchased.getPrice()
                                                .multiply(BigDecimal.valueOf(sold.getRemainingQty()))));

                        purchased.setRemainingQty(purchased.getRemainingQty() - sold.getRemainingQty());
                        sold.setRemainingQty(0);
                        invoiceProductRepository.save(purchased);

                    } else if (purchased.getRemainingQty() < sold.getRemainingQty()) {
                        purchaseTotalPrice = purchaseTotalPrice
                                .add(purchased.getPrice()
                                        .multiply(BigDecimal.valueOf(purchased.getRemainingQty()))
                                        .multiply(purchased.getTax())
                                        .divide(BigDecimal.valueOf(100))
                                        .add(purchased.getPrice()
                                                .multiply(BigDecimal.valueOf(purchased.getRemainingQty()))));

                        sold.setRemainingQty(sold.getRemainingQty() - purchased.getRemainingQty()); // to check next purchase invoice product with this quantity amount
                        purchased.setRemainingQty(0);
                        invoiceProductRepository.save(purchased);

                    } else { // when sales remaining quantity and purchase remaining quantity is equal
                        purchaseTotalPrice = purchaseTotalPrice
                                .add(purchased.getPrice()
                                        .multiply(BigDecimal.valueOf(purchased.getRemainingQty()))
                                        .multiply(purchased.getTax())
                                        .divide(BigDecimal.valueOf(100))
                                        .add(purchased.getPrice()
                                                .multiply(BigDecimal.valueOf(purchased.getRemainingQty()))));

                        purchased.setRemainingQty(0);
                        sold.setRemainingQty(0);
                        invoiceProductRepository.save(purchased);
                    }
                }
            }

            profitLoss = salesTotalPrice.subtract(purchaseTotalPrice);
            sold.setProfitLoss(profitLoss);
            invoiceProductRepository.save(sold);

        }
    }

    @Override
    public List<InvoiceProduct> getAllApprovedInvoiceProductsByCompany(CompanyDto company) {
        return invoiceProductRepository
                .findAllByInvoice_InvoiceStatusAndInvoice_CompanyOrderByInvoice_DateDesc(
                        InvoiceStatus.APPROVED, mapperUtil.convert(company, new Company()));
    }

    private BigDecimal calculate(InvoiceProductDto invoiceProductDto) {
        BigDecimal totalWithOutTax = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()));
        BigDecimal taxAmount = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity())).multiply(invoiceProductDto.getTax()).divide(new BigDecimal(100));

        return totalWithOutTax.add(taxAmount).setScale(2, RoundingMode.CEILING);
    }

}
