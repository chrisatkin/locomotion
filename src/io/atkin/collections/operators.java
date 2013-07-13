package io.atkin.collections;

import io.atkin.collections.types.Tuple;

public class operators {
	public static <T1, T2> Tuple<T1, T2> i(T1 _item_1, T2 _item_2) {
		return new Tuple<T1, T2>(_item_1, _item_2);
	}
}
