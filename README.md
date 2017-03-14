------Generate certificate ----
keytool -genkey -alias keyAlias -keyalg RSA -keypass yourkeypass -storepass yourpassword -keystore privateKey.store

------ For an expired certificate ------
delete it, then generate a new one.

------ delete certificate-------
keytool -delete
 -alias keyAlias
 -keystore keystore-name
 -storepass password


References:
https://docs.oracle.com/cd/E19798-01/821-1751/ghleq/


------------code to serialize and deserialize fingerprints
try {
	    	
			String fingerPrintSample =  Base64.getEncoder().encodeToString(this.getSampleForFingerprint().serialize());
        	
        	//on the server-side
        	byte[] bytes = Base64.getDecoder().decode(fingerPrintSample);
        	
        	
        	DPFPSampleFactoryImpl nnn = new DPFPSampleFactoryImpl();
        	DPFPSample mySample = nnn.createSample(bytes);
	        
	    }
	    catch (Exception jse) {
	        this.displayErrorMessage(jse.getMessage());
	    }
