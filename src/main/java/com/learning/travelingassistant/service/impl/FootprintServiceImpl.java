package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.dto.FootprintDTO;
import com.learning.travelingassistant.entity.FootprintCollaborator;
import com.learning.travelingassistant.entity.FootprintPhoto;
import com.learning.travelingassistant.entity.TravelFootprint;
import com.learning.travelingassistant.mapper.FootprintMapper;
import com.learning.travelingassistant.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    private FootprintMapper footprintMapper;

    @Override
    @Transactional
    public void createFootprint(Long creatorId, FootprintDTO dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("相册标题不能为空");
        }
        if (dto.getProvinceId() == null) {
            throw new IllegalArgumentException("省份ID不能为空");
        }

        TravelFootprint footprint = new TravelFootprint();
        footprint.setCreatorId(creatorId);
        footprint.setProvinceId(dto.getProvinceId());
        footprint.setTitle(dto.getTitle());
        footprint.setStartDate(dto.getStartDate());
        footprint.setEndDate(dto.getEndDate());

        footprintMapper.insertFootprint(footprint);

        FootprintCollaborator creatorCollaborator = new FootprintCollaborator();
        creatorCollaborator.setFootprintId(footprint.getId());
        creatorCollaborator.setUserId(creatorId);
        footprintMapper.insertCollaborator(creatorCollaborator);

        if (dto.getFriendIds() != null && !dto.getFriendIds().isEmpty()) {
            for (Long friendId : dto.getFriendIds()) {
                FootprintCollaborator collaborator = new FootprintCollaborator();
                collaborator.setFootprintId(footprint.getId());
                collaborator.setUserId(friendId);
                footprintMapper.insertCollaborator(collaborator);
            }
        }
    }

    @Override
    public List<TravelFootprint> listFootprints(Long provinceId, Long userId) {
        return footprintMapper.findByProvinceAndUser(provinceId, userId);
    }

    @Override
    public List<TravelFootprint> listAllFootprints(Long userId) {
        return footprintMapper.findAllByUser(userId);
    }

    @Override
    public Map<String, Object> getFootprintDetail(Long footprintId, Long userId) {
        FootprintCollaborator collaborator = footprintMapper.findCollaborator(footprintId, userId);
        if (collaborator == null) {
            throw new IllegalArgumentException("您没有权限查看此相册");
        }

        TravelFootprint footprint = footprintMapper.findById(footprintId);
        List<FootprintPhoto> photos = footprintMapper.findPhotosByFootprint(footprintId);
        List<FootprintCollaborator> collaborators = footprintMapper.findCollaboratorsByFootprint(footprintId);

        Map<String, Object> result = new HashMap<>();
        result.put("footprint", footprint);
        result.put("photos", photos);
        result.put("collaborators", collaborators);

        return result;
    }

    @Override
    @Transactional
    public void addPhoto(Long userId, Long footprintId, String imageUrl) {
        FootprintCollaborator collaborator = footprintMapper.findCollaborator(footprintId, userId);
        if (collaborator == null) {
            throw new IllegalArgumentException("您没有权限上传照片到此相册");
        }

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("图片URL不能为空");
        }

        FootprintPhoto photo = new FootprintPhoto();
        photo.setFootprintId(footprintId);
        photo.setUploaderId(userId);
        photo.setImageUrl(imageUrl);

        footprintMapper.insertPhoto(photo);
    }

    @Override
    @Transactional
    public void deletePhoto(Long userId, Long photoId) {
        FootprintPhoto photo = footprintMapper.findPhotoById(photoId);
        if (photo == null) {
            throw new IllegalArgumentException("照片不存在");
        }

        TravelFootprint footprint = footprintMapper.findById(photo.getFootprintId());

        if (!photo.getUploaderId().equals(userId) && !footprint.getCreatorId().equals(userId)) {
            throw new IllegalArgumentException("您没有权限删除此照片");
        }

        footprintMapper.deletePhoto(photoId);
    }
}