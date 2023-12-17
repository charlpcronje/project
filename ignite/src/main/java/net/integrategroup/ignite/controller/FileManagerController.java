package net.integrategroup.ignite.controller;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.integrategroup.ignite.reports.ReportEngine;

@Controller
@RequestMapping("/file-manager")
public class FileManagerController {

	@Autowired
	ReportEngine reportEngine;

//	private Logger logger= Logger.getLogger(getClass().getName());

	@PostMapping(path="/report/upload", consumes="multipart/form-data")
	public String upload(HttpServletRequest request, MultipartRequest multipartRequest, RedirectAttributes redirectAttributes) {
		String successResult = "redirect:/upload-success";
		String failResult = "redirect:/upload-failure?e=";

		// get the file parts/info that we will need
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		if (fileMap.size() == 0) {
			return failResult + "No file was attached to the request.";
		}

		if (fileMap.size() != 1) {
			return failResult + "Only one file must be submitted at a time.";
		}

		Iterator<String> it = fileMap.keySet().iterator();
		String fileMapEntry = it.next(); // If we want to upload multiple files: have to loop here though all the files
		MultipartFile multipartFile = fileMap.get(fileMapEntry);

		try {
			reportEngine.uploadReport(multipartFile);
		} catch (Exception e) {
			e.printStackTrace();

			return failResult + e.getMessage();
		}

		return successResult;
	}

}
