//import com.google.cloud.vision.v1.AnnotateImageRequest;
//import com.google.cloud.vision.v1.AnnotateImageResponse;
//import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
//import com.google.cloud.vision.v1.EntityAnnotation;
//import com.google.cloud.vision.v1.Feature;
//import com.google.cloud.vision.v1.Image;
//import com.google.cloud.vision.v1.ImageAnnotatorClient;
//import com.google.cloud.vision.v1.ImageSource;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DetectLabelsGcs {
//
//    public static void detectLabelsGcs() throws IOException {
//        // TODO(developer): Replace these variables before running the sample.
//        String filePath = "gs://your-gcs-bucket/path/to/image/file.jpg";
//        detectLabelsGcs(filePath);
//    }
//
//    // Detects labels in the specified remote image on Google Cloud Storage.
//    public static void detectLabelsGcs(String gcsPath) throws IOException {
//        List<AnnotateImageRequest> requests = new ArrayList<>();
//
//        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
//        Image img = Image.newBuilder().setSource(imgSource).build();
//        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
//        AnnotateImageRequest request =
//                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
//        requests.add(request);
//
//        // Initialize client that will be used to send requests. This client only needs to be created
//        // once, and can be reused for multiple requests. After completing all of your requests, call
//        // the "close" method on the client to safely clean up any remaining background resources.
//        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
//            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
//            List<AnnotateImageResponse> responses = response.getResponsesList();
//
//            for (AnnotateImageResponse res : responses) {
//                if (res.hasError()) {
//                    System.out.format("Error: %s%n", res.getError().getMessage());
//                    return;
//                }
//
//                // For full list of available annotations, see http://g.co/cloud/vision/docs
//                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
//                    annotation
//                            .getAllFields()
//                            .forEach((k, v) -> System.out.format("%s : %s%n", k, v.toString()));
//                }
//            }
//        }
//    }
//}