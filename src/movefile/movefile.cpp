// movefile.cpp : Defines the exported functions for the DLL application.
//

#include <jni.h>
#include <stdio.h>
#include "Patcher.h"
#include <windows.h>
#include <string>
#include <fstream>
#include <iostream>

using namespace std;

// Implementation of native method moveFile() of Patcher class
JNIEXPORT void JNICALL Java_Patcher_moveFile (JNIEnv *env, jobject obj, jstring src, jstring dest) {
	const char *srcChar = env->GetStringUTFChars(src, NULL);
	const char *destChar = env->GetStringUTFChars(dest, NULL);
	string englishXmlPath(srcChar);
	string japaneseXmlPath(destChar);
	ofstream tempXML;
	tempXML.open("C:\\Program Files (x86)\\NCJapan\\contents\\Local\\NCJAPAN\\JAPANESE\\data\\XML.TXT", ofstream::out | ofstream::trunc);
	if (!tempXML.is_open()) {
		ofstream errorFile;
		errorFile.open("C:\\Users\\Debashish\\Desktop\\errorFile.txt");
		char *errBuff = new char[256];
		strerror_s(errBuff, 256, errno);
		errorFile << "Error: " << errBuff;
	}

	tempXML << "this is Debashish's file";

	tempXML.close();
	env->ReleaseStringUTFChars(src, srcChar);
	env->ReleaseStringUTFChars(dest, destChar);
	return;
}

