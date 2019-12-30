package com.tedu.day04;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * 播放背景音乐的工具类
 */
@SuppressWarnings("deprecation")
public class Music{
	//属性
	static URL bgUrl_1;
	static AudioClip bg1;
	static {
		try {
			bgUrl_1 = new File("music/Vive.wav").toURL();
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		bg1=Applet.newAudioClip(bgUrl_1);
	}
}