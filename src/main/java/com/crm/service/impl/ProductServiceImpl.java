package com.crm.service.impl;

import com.crm.dao.ProductDao;
import com.crm.dao.impl.ProductDaoImpl;
import com.crm.entities.Product;
import com.crm.service.ProductService;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductDao PRODUCT_DAO = new ProductDaoImpl(Product.class);

    @Override
    public Optional<Product> findById(int id) {
        return PRODUCT_DAO.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return PRODUCT_DAO.findAll();
    }

    @Override
    public void delete(Product product) {
        PRODUCT_DAO.delete(product);
    }

    @Override
    public void update(Product product) {
        PRODUCT_DAO.update(product);
    }

    @Override
    public void saveOrUpdate(Product product) {
        File imgFile = product.getImgFile();
        String copyToUrl = System.getProperty("user.dir") + "/src/main/resources/images/" + imgFile.getName();
//        imgFile.renameTo(new File(url));
        File copied = new File(copyToUrl);
        try (
                InputStream in = new BufferedInputStream(
                        new FileInputStream(imgFile));
                OutputStream out = new BufferedOutputStream(
                        new FileOutputStream(copied))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setImgUrl(copyToUrl);
        PRODUCT_DAO.saveOrUpdate(product);
    }
}
