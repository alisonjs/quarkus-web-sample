package org.acme;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Optional;

@Path("products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @GET
    public List<Product> listAllProducts() {
        return Product.listAll();
    }

    @GET()
    @Path("/{id}")
    public Product findOne(@PathParam("id") Long id) {
        Optional<Product> product = Product.findByIdOptional(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new NotFoundException();
    }

    @POST
    @Transactional
    public void registerProduct(ProductRegisterDTO productRegisterDTO) {
        Product newProduct = new Product(productRegisterDTO.name(), productRegisterDTO.value());
        newProduct.persist();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void updateValue(@PathParam("id") Long id, UpdateProductValueDTO updateProductValueDTO) {
        Optional<Product> product = Product.findByIdOptional(id);
        if (product.isPresent()) {
            Product p = product.get();
            p.setValue(updateProductValueDTO.value());
            p.persist();
        } else {
            throw new NotFoundException();
        }
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Product.findByIdOptional(id).ifPresentOrElse(p -> p.delete(), () -> {
            throw new NotFoundException();
        });
    }

}
