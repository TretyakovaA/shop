package io.swagger.mapper;

import io.swagger.dto.AdDto;
import io.swagger.exceptions.UserNotFoundException;
import io.swagger.model.Ad;
import io.swagger.model.StoredImage;
import io.swagger.repository.StoredImageRepository;
import io.swagger.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdDtoMapper {
    private final UserRepository userRepository;
    private final StoredImageRepository storedImageRepository;

    public AdDtoMapper(UserRepository userRepository, StoredImageRepository storedImageRepository) {
        this.userRepository = userRepository;
        this.storedImageRepository = storedImageRepository;
    }

    public Ad toEntity(AdDto adDto){
        Ad ad = new Ad();
        ad.setPk(adDto.getPk());
        List<StoredImage> tempList = new ArrayList<>();
        for (int i = 0; i < adDto.getImage().size(); i++){
            StoredImage temp = storedImageRepository.save (new StoredImage(adDto.getImage().get(i)));
            tempList.add(temp);
        }
        ad.setStoredImage(tempList);
        ad.setPrice(ad.getPrice());
        ad.setTitle(ad.getTitle());
        ad.setAuthor(userRepository.findById(adDto.getAuthor()).orElseThrow(
                () -> {
                    throw new UserNotFoundException(adDto.getAuthor());
                }
        ));
        return ad;
    }


    public AdDto toDto(Ad ad){
        AdDto adDto = new AdDto();
        adDto.setPk(ad.getPk());
        adDto.setAuthor(ad.getAuthor().getId());
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < ad.getStoredImage().size(); i++){
            String temp =  ad.getStoredImage().get(i).getPath();
            tempList.add(temp);
        }
        adDto.setImage(tempList);
        adDto.setPrice(ad.getPrice());
        adDto.setTitle(ad.getTitle());
        return adDto;
    }

    public List<AdDto> toDtos(List<Ad> adList){
        List<AdDto> adDtos = new ArrayList<>();
        for (int i = 0; i < adList.size(); i++){
            adDtos.add(toDto(adList.get(i)));
        }
        return adDtos;
    }
}
