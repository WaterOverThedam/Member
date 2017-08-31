package com.thelittlegym.mobile.utils.msg.lib.base;

import com.thelittlegym.mobile.utils.msg.entity.DataStore;

public abstract class SenderWapper {

	protected DataStore requestData = new DataStore();

	public abstract ISender getSender();
}
