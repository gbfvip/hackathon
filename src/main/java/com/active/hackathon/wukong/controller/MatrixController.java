package com.active.hackathon.wukong.controller;

import com.active.hackathon.wukong.common.TaskStatus;
import com.active.hackathon.wukong.controller.response.AggregationResponse;
import com.active.hackathon.wukong.controller.response.MatrixResponse;
import com.active.hackathon.wukong.domain.WordSearchAggregation;
import com.active.hackathon.wukong.repository.FileTaskRepository;
import com.active.hackathon.wukong.repository.FileTaskWordRepository;
import com.active.hackathon.wukong.repository.KeywordTaskRepository;
import com.active.hackathon.wukong.repository.KeywordTaskWordRepository;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * get matrix info
 */
@RestController
@RequestMapping(path = "/matrix")
@RequiredArgsConstructor
public class MatrixController {
    private final FileTaskWordRepository fileTaskWordRepository;
    private final KeywordTaskWordRepository keywordTaskWordRepository;
    private final KeywordTaskRepository keywordTaskRepository;
    private final FileTaskRepository fileTaskRepository;

    @GetMapping
    public MatrixResponse getFileTaskMatrix() {
        long fileTaskCount = fileTaskRepository.countByStatusIn(Lists.newArrayList(TaskStatus.DONE));
        List<WordSearchAggregation> fileAggregations = fileTaskWordRepository.findWordAggregation();
        AggregationResponse file = AggregationResponse.from(fileTaskCount, fileAggregations);
        long keywordTaskCount = keywordTaskRepository.countByStatusIn(Lists.newArrayList(TaskStatus.DONE));
        List<WordSearchAggregation> keywordAggregations = keywordTaskWordRepository.findWordAggregation();
        AggregationResponse keyword = AggregationResponse.from(keywordTaskCount, keywordAggregations);
        return MatrixResponse.builder()
          .file(file).keyword(keyword).build();
    }
}
