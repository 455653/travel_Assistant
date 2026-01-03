package com.learning.travelingassistant.service.impl;

import com.learning.travelingassistant.dto.FootprintDTO;
import com.learning.travelingassistant.dto.FootprintItemDTO;
import com.learning.travelingassistant.entity.FootprintCollaborator;
import com.learning.travelingassistant.entity.FootprintNote;
import com.learning.travelingassistant.entity.FootprintPhoto;
import com.learning.travelingassistant.entity.TravelFootprint;
import com.learning.travelingassistant.mapper.FootprintMapper;
import com.learning.travelingassistant.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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
        // 权限校验：检查用户是否是协作者或创建者
        FootprintCollaborator collaborator = footprintMapper.findCollaborator(footprintId, userId);
        if (collaborator == null) {
            throw new IllegalArgumentException("您没有权限查看此相册");
        }

        TravelFootprint footprint = footprintMapper.findById(footprintId);
        List<FootprintPhoto> photos = footprintMapper.findPhotosByFootprint(footprintId);
        List<FootprintNote> notes = footprintMapper.findNotesByFootprint(footprintId);
        List<FootprintCollaborator> collaborators = footprintMapper.findCollaboratorsByFootprint(footprintId);

        // 将照片和文字记录混合成统一的时间流
        List<FootprintItemDTO> items = new ArrayList<>();
        
        // 添加照片
        for (FootprintPhoto photo : photos) {
            FootprintItemDTO item = new FootprintItemDTO();
            item.setType("PHOTO");
            item.setContent(photo.getImageUrl());
            item.setAuthorName(photo.getUploaderUsername());
            item.setCreateTime(photo.getCreateTime());
            item.setItemId(photo.getId());
            items.add(item);
        }
        
        // 添加文字记录
        for (FootprintNote note : notes) {
            FootprintItemDTO item = new FootprintItemDTO();
            item.setType("NOTE");
            item.setContent(note.getContent());
            item.setAuthorName(note.getAuthorUsername());
            item.setCreateTime(note.getCreateTime());
            item.setItemId(note.getId());
            items.add(item);
        }
        
        // 按时间倒序排序
        items.sort(Comparator.comparing(FootprintItemDTO::getCreateTime).reversed());

        Map<String, Object> result = new HashMap<>();
        result.put("footprint", footprint);
        result.put("items", items);  // 混合的照片和文字
        result.put("photos", photos);  // 保留原有的照片列表（兼容性）
        result.put("collaborators", collaborators);
        result.put("isCreator", footprint.getCreatorId().equals(userId));  // 标识当前用户是否为创建者

        return result;
    }

    @Override
    @Transactional
    public void addPhoto(Long userId, Long footprintId, String imageUrl) {
        // 权限校验：必须是协作者或创建者
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

        // 权限校验：仅照片上传者或相册创建者可删除
        if (!photo.getUploaderId().equals(userId) && !footprint.getCreatorId().equals(userId)) {
            throw new IllegalArgumentException("您没有权限删除此照片");
        }

        footprintMapper.deletePhoto(photoId);
    }

    @Override
    @Transactional
    public void deleteFootprint(Long userId, Long footprintId) {
        TravelFootprint footprint = footprintMapper.findById(footprintId);
        if (footprint == null) {
            throw new IllegalArgumentException("足迹相册不存在");
        }

        // 权限校验：仅创建者可删除整个相册
        if (!footprint.getCreatorId().equals(userId)) {
            throw new IllegalArgumentException("只有创建者可以删除此相册");
        }

        // 执行删除（数据库外键设置了级联删除，会自动删除关联的照片、文字和协作关系）
        footprintMapper.deleteFootprint(footprintId);
    }

    @Override
    @Transactional
    public void updateTitle(Long userId, Long footprintId, String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("相册标题不能为空");
        }

        TravelFootprint footprint = footprintMapper.findById(footprintId);
        if (footprint == null) {
            throw new IllegalArgumentException("足迹相册不存在");
        }

        // 权限校验：仅创建者可修改标题
        if (!footprint.getCreatorId().equals(userId)) {
            throw new IllegalArgumentException("只有创建者可以修改相册标题");
        }

        footprintMapper.updateTitle(footprintId, title);
    }

    @Override
    @Transactional
    public void addNote(Long userId, Long footprintId, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("文字内容不能为空");
        }

        // 权限校验：必须是协作者或创建者
        FootprintCollaborator collaborator = footprintMapper.findCollaborator(footprintId, userId);
        if (collaborator == null) {
            throw new IllegalArgumentException("您没有权限在此相册发布文字");
        }

        FootprintNote note = new FootprintNote();
        note.setFootprintId(footprintId);
        note.setAuthorId(userId);
        note.setContent(content);

        footprintMapper.insertNote(note);
    }
}