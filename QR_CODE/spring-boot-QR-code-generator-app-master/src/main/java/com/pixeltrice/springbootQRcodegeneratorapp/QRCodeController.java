package com.pixeltrice.springbootQRcodegeneratorapp;


import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.json.JSONObject;

@RestController
public class QRCodeController {
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
	private final int WIDTH = 300;
	private final int HEIGHT = 300;
	private final String QR_TEXT = "Spring Boot REST API to generate QR Code - Websparrow.org";

	
    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
		public void download(
				@PathVariable("codeText") String codeText,
				@PathVariable("width") Integer width,
				@PathVariable("height") Integer height)
			    throws Exception {
			        QRCodeGenerator.generateQRCodeImage(codeText, width, height, QR_CODE_IMAGE_PATH);
			    }



    @RequestMapping(value = "/genrateQRCode" ,method=RequestMethod.POST)
   	public ResponseEntity<byte[]> generateQRCode(@ModelAttribute  Developer developer)
   		    throws Exception {
   		    	return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(QRCodeGenerator.getQRCodeImage(generateJson( developer), WIDTH, HEIGHT));
   		    }

	@GetMapping("/greeting")
	public ModelAndView greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		ModelAndView mav = new ModelAndView("developer");
		Developer dev = new Developer();
		mav.addObject("developer", dev);
		return mav;
	}

    String generateJson(Developer developer) throws JSONException {
		JSONObject jsonObjstr= new JSONObject();
		jsonObjstr.put("android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME",developer.getFirstName());
		jsonObjstr.put("android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION",developer.getLastName());
		jsonObjstr.put("android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM",developer.getEmail());
        return jsonObjstr.toString();
	}
}
