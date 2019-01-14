package com.gcv.labeldetection.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gcv.labeldetection.entity.Label;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

@Service
public class LabelDetection {
	
	public List<Label> labelDetect(byte[] data) throws Exception {
		List<Label> labelList = new ArrayList<>();
		// Instantiates a client
		 try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
		   ByteString imgBytes = ByteString.copyFrom(data);
		
		   // Builds the image annotation request
		   List<AnnotateImageRequest> requests = new ArrayList<>();
		   Image img = Image.newBuilder().setContent(imgBytes).build();
		   Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
		   AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
		       .addFeatures(feat)
		       .setImage(img)
		       .build();
		   requests.add(request);
		
		   // Performs label detection on the image file
		   BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
		   List<AnnotateImageResponse> responses = response.getResponsesList();
		
		   for (AnnotateImageResponse res : responses) {
		     if (res.hasError()) {
		       System.out.printf("Error: %s\n", res.getError().getMessage());
		       return null;
		     }

		     for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
		    	 Label label = new Label();
		    	 label.setDescription(annotation.getDescription());
		    	 label.setScore(annotation.getScore());
		    	 label.setTopicality(annotation.getTopicality());
		    	 
		    	 labelList.add(label);
		     }
		   }
		 }
		 return labelList;
	}
}