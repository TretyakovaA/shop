package io.shop.mapper;

import io.shop.dto.AdBodyDto;
import io.shop.dto.CreateAdDto;
import io.shop.model.Ad;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdBodyDtoMapper {

    public Ad toEntity(AdBodyDto body){
        Ad ad = new Ad();
        ad.setDescription(body.getProperties().getDescription());
        ad.setTitle(body.getProperties().getTitle());
        ad.setPrice(body.getProperties().getPrice());
        ad.setStoredImage(List.of(body.getImage()));
        return ad;
    }

    public AdBodyDto toDto(Ad ad){
        AdBodyDto adBodyDto = new AdBodyDto ();
        if (ad.getStoredImage() != null){
            adBodyDto.setImage(ad.getStoredImage().get(0));
        }
        CreateAdDto createAdDto = new CreateAdDto();
        createAdDto.setDescription(ad.getDescription());
        createAdDto.setTitle(ad.getTitle());
        createAdDto.setPrice(ad.getPrice());
        adBodyDto.setProperties(createAdDto);
        return adBodyDto;
    }

    public List<AdBodyDto> toDtos(List<Ad> adsList){
        List<AdBodyDto> adBodyDtos = new ArrayList<>();
        for (int i = 0; i < adsList.size(); i++){
            adBodyDtos.add(toDto(adsList.get(i)));
        }
        return adBodyDtos;
    }
}
