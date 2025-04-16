package lk.ijse.heladivaproject.util;


import lk.ijse.heladivaproject.dto.*;
import lk.ijse.heladivaproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper mapper;
    public UserDTO toUserDTO(User user) {
        return  mapper.map(user, UserDTO.class);
    }
    public User toUser(UserDTO userDTO) {
        return  mapper.map(userDTO, User.class);
    }
    public Collection<UserDTO> toUserDTOs(List<User> all) {
        return mapper.map(all, Collection.class);
    }

    public ArticleDTO toArticleDTO(Article article){return mapper.map(article,ArticleDTO.class);}
    public Article toArticle(ArticleDTO articleDTO){return  mapper.map(articleDTO,Article.class);}
    public List<ArticleDTO> toArticleDTOS(List<Article> all) {
        return all.stream().map(this::toArticleDTO).collect(Collectors.toList());
    }


    public ProductDTO toProductDTO(Product product){return mapper.map(product,ProductDTO.class);}
    public Product toProduct(ProductDTO productDTO){return  mapper.map(productDTO,Product.class);}
    public List<ProductDTO> toProductDTOS(List<Product> all) {
        return all.stream().map(this::toProductDTO).collect(Collectors.toList());
    }



    public OrderDTO toOrderDTO(Order order) {
        return mapper.map(order, OrderDTO.class);
    }
    public Order toOrder(OrderDTO orderDTO) {
        return mapper.map(orderDTO, Order.class);
    }
    public List<OrderDTO> toOrderDTOS(List<Order> all) {
        return all.stream().map(this::toOrderDTO).collect(Collectors.toList());
    }


    public OrderDetailsDTO toOrderDetailsDTO(OrderDetails orderDetails) {
        return mapper.map(orderDetails, OrderDetailsDTO.class);
    }
    public OrderDetails toOrderDetails(OrderDetailsDTO orderDetailsDTO) {
        return mapper.map(orderDetailsDTO, OrderDetails.class);
    }
    public List<OrderDetailsDTO> toOrderDetailsDTOS(List<OrderDetails> all) {
        return all.stream().map(this::toOrderDetailsDTO).collect(Collectors.toList());
    }

    public Medicine toMedicine(MedicineDTO medicineDTO) {
        return mapper.map(medicineDTO, Medicine.class);
    }

    public MedicineDTO toMedicineDTO(Medicine medicine) {
        return mapper.map(medicine, MedicineDTO.class);
    }


    public List<MedicineDTO> toMedicineDTOS(List<Medicine> all) {
        return all.stream().map(this::toMedicineDTO).collect(Collectors.toList());
    }
}
