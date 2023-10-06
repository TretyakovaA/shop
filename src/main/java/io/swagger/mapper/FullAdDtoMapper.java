package io.swagger.mapper;

import io.swagger.dto.FullAdDto;
import io.swagger.model.Ad;
import io.swagger.model.User;
import io.swagger.repository.StoredImageRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FullAdDtoMapper {

    private final UserRepository userRepository;
    private final StoredImageRepository storedImageRepository;

    public FullAdDtoMapper(UserRepository userRepository, StoredImageRepository storedImageRepository) {
        this.userRepository = userRepository;
        this.storedImageRepository = storedImageRepository;
    }

    public  FullAdDto toDto(Ad ad, User author){
        FullAdDto fullAdDto = new FullAdDto();
        fullAdDto.setPk(ad.getPk());
        fullAdDto.setDescription(ad.getDescription());
        fullAdDto.setEmail(author.getEmail());
        fullAdDto.setPhone(author.getPhone());
        fullAdDto.setAuthorFirstName(author.getFirstName());
        fullAdDto.setAuthorLastName(author.getLastName());
        fullAdDto.setPrice(ad.getPrice());
        fullAdDto.setTitle(ad.getTitle());
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < ad.getStoredImage().size(); i++){
            String temp =  ad.getStoredImage().get(i).getPath();
            tempList.add(temp);
        }
        fullAdDto.setImage(tempList);
        return fullAdDto;
    }

}
