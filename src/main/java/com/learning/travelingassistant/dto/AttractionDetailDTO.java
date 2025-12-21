package com.learning.travelingassistant.dto;

import com.learning.travelingassistant.entity.Attraction;
import com.learning.travelingassistant.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDetailDTO {
    private Attraction attraction;
    private List<Comment> comments;
}
