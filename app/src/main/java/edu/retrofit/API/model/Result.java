package edu.retrofit.API.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//classe pertencente ao MovieResults (representa cada filme)
public class Result {

	@SerializedName("title")
	@Expose
	private String title;
	@SerializedName("original_language")
	@Expose
	private String originalLanguage;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}
}
