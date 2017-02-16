package com.vanxd.admin.ueditor.upload;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.ueditor.define.AppInfo;
import com.vanxd.admin.ueditor.define.BaseState;
import com.vanxd.admin.ueditor.define.State;
import com.vanxd.admin.util.FileUtil;
import com.vanxd.admin.util.SpringContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class BinaryUploader{

	/**
	 * 保存文件
	 *
	 * Spring会将Request中的文件截取，所以我们需要先转换成Spring的文件请求才能获取到文件数据
	 * @param request
	 * @param conf
	 * @return
	 */
	public static final State save(HttpServletRequest request,
								   Map<String, Object> conf) {
		List<MultipartFile> uploadFiles = new ArrayList<>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				uploadFiles.add(multiRequest.getFile(iter.next().toString()));
				break;
			}
		}
		if (CollectionUtils.isEmpty(uploadFiles)) {
			return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
		}
		if ( uploadFiles.size() > 1 ) {
			throw new BusinessException("该功能只能上传一个文件！");
		}
		ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
		FileUtil fileUtil = applicationContext.getBean(FileUtil.class);
		MultipartFile targetFile = uploadFiles.get(0);
		String suffix = fileUtil.getSuffix(targetFile);
		if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
			return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
		}
		FileUtil.UploadFile uploadResult = fileUtil.save(targetFile);
		BaseState baseState = new BaseState(true);
		baseState.putInfo( "size", targetFile.getSize() );
		baseState.putInfo( "title", targetFile.getName() );
		baseState.putInfo( "url",  uploadResult.getUrl());
		return baseState;
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
}
