/**
 * 
 */
package com.bkc.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author Akash
 *
 * @Date 05-Oct-2017
 */
public class MyEnumDeserialize extends JsonDeserializer<ShopType> {

  /*  @Override
    public ShopType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        MyEnum type = null;
        try{
            if(node.get("attr") != null){
                type = MyEnum.get(Long.parseLong(node.get("attr").asText()));
                if (type != null) {
                    return type;
                }
            }
        }catch(Exception e){
            type = null;
        }
        return type;
    }*/

	/* (non-Javadoc)
	 * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser, com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public ShopType deserialize(JsonParser jsonParser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		
		 JsonNode node = jsonParser.getCodec().readTree(jsonParser);
	        ShopType type = null;
	        try{
	            if(node.get("attr") != null){
	                type = ShopType.Fertilizer;
	                if (type != null) {
	                    return type;
	                }
	            }
	        }catch(Exception e){
	            type = null;
	        }
	        return type;
		
		
	}
}