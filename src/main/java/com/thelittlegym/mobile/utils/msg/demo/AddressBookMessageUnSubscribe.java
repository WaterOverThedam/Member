package com.thelittlegym.mobile.utils.msg.demo;

import com.thelittlegym.mobile.utils.msg.config.AppConfig;
import com.thelittlegym.mobile.utils.msg.lib.ADDRESSBOOKMessage;
import com.thelittlegym.mobile.utils.msg.utils.ConfigLoader;

public class AddressBookMessageUnSubscribe {

	public static void main(String[] args) {

		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		ADDRESSBOOKMessage addressbook = new ADDRESSBOOKMessage(config);
		addressbook.setAddress("186****1889");
		addressbook.unsubscribe();
	}	
}
