package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.core.util.TimeUtil;
import com.example.web.service.TimeRecordService;

import lombok.RequiredArgsConstructor;

/**
 * 시간 관련 웹 요청을 처리하는 컨트롤러입니다.
 */
@Controller
@RequiredArgsConstructor
public class TimeController {
	private final TimeRecordService timeRecordService;

	/**
	 * 메인 페이지를 표시합니다.
	 * 현재 시각을 보여주고 접속 기록을 저장합니다.
	 *
	 * @param model Spring MVC 모델
	 * @return 뷰 이름
	 */
	@GetMapping("/time")
	public String showTime(Model model) {
		String currentTime = TimeUtil.getCurrentTime();
		timeRecordService.saveTimeRecord(currentTime, "Page accessed");

		model.addAttribute("currentTime", currentTime);
		model.addAttribute("timeRecords", timeRecordService.getAllTimeRecords());
		return "time";
	}
}
