package com.learning.travelingassistant.controller;

import com.learning.travelingassistant.common.Result;
import com.learning.travelingassistant.dto.FootprintDTO;
import com.learning.travelingassistant.entity.TravelFootprint;
import com.learning.travelingassistant.service.FootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/footprint")
public class FootprintController {

    @Autowired
    private FootprintService footprintService;

    @GetMapping("/list")
    public Result<List<TravelFootprint>> listFootprints(
            @RequestParam Long provinceId,
            @RequestParam Long userId) {
        try {
            List<TravelFootprint> footprints = footprintService.listFootprints(provinceId, userId);
            return Result.success(footprints);
        } catch (Exception e) {
            return Result.error("获取足迹列表失败: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public Result<List<TravelFootprint>> listAllFootprints(@RequestParam Long userId) {
        try {
            List<TravelFootprint> footprints = footprintService.listAllFootprints(userId);
            return Result.success(footprints);
        } catch (Exception e) {
            return Result.error("获取所有足迹失败: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public Result<String> createFootprint(@RequestBody Map<String, Object> params) {
        try {
            Long creatorId = Long.parseLong(params.get("creatorId").toString());

            FootprintDTO dto = new FootprintDTO();
            dto.setProvinceId(Long.parseLong(params.get("provinceId").toString()));
            dto.setTitle(params.get("title").toString());

            if (params.get("startDate") != null) {
                dto.setStartDate(java.time.LocalDate.parse(params.get("startDate").toString()));
            }
            if (params.get("endDate") != null) {
                dto.setEndDate(java.time.LocalDate.parse(params.get("endDate").toString()));
            }

            if (params.get("friendIds") != null) {
                @SuppressWarnings("unchecked")
                List<Integer> friendIdInts = (List<Integer>) params.get("friendIds");
                dto.setFriendIds(friendIdInts.stream().map(Long::valueOf).toList());
            }

            footprintService.createFootprint(creatorId, dto);
            return Result.success("足迹创建成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("创建足迹失败: " + e.getMessage());
        }
    }

    @GetMapping("/detail")
    public Result<Map<String, Object>> getDetail(
            @RequestParam Long id,
            @RequestParam Long userId) {
        try {
            Map<String, Object> detail = footprintService.getFootprintDetail(id, userId);
            return Result.success(detail);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("获取详情失败: " + e.getMessage());
        }
    }

    @PostMapping("/photo/add")
    public Result<String> addPhoto(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long footprintId = Long.parseLong(params.get("footprintId").toString());
            String imageUrl = params.get("imageUrl").toString();

            footprintService.addPhoto(userId, footprintId, imageUrl);
            return Result.success("照片上传成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("上传照片失败: " + e.getMessage());
        }
    }

    @PostMapping("/photo/delete")
    public Result<String> deletePhoto(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long photoId = Long.parseLong(params.get("photoId").toString());

            footprintService.deletePhoto(userId, photoId);
            return Result.success("照片删除成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除照片失败: " + e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result<String> deleteFootprint(@RequestParam Long id, @RequestParam Long userId) {
        try {
            footprintService.deleteFootprint(userId, id);
            return Result.success("相册删除成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("删除相册失败: " + e.getMessage());
        }
    }

    @PostMapping("/update/title")
    public Result<String> updateTitle(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long footprintId = Long.parseLong(params.get("footprintId").toString());
            String title = params.get("title").toString();

            footprintService.updateTitle(userId, footprintId, title);
            return Result.success("标题修改成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("修改标题失败: " + e.getMessage());
        }
    }

    @PostMapping("/note/add")
    public Result<String> addNote(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.parseLong(params.get("userId").toString());
            Long footprintId = Long.parseLong(params.get("footprintId").toString());
            String content = params.get("content").toString();

            footprintService.addNote(userId, footprintId, content);
            return Result.success("文字发布成功");
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        } catch (Exception e) {
            return Result.error("发布文字失败: " + e.getMessage());
        }
    }
}