package edu.hrbust.iot.amqp.web.utils.common;

import java.util.HashMap;

public class WebMapResponse<K, V> extends HashMap<K, V> {
   public WebMapResponse<K, V> putData(K k, V v){
       this.put(k, v);
       return this;
   }
}
