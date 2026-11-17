package com.ai.functioncall.pdd.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class OrderManageService {

	public String getOrderById(String orderId) {
		return "订单号：" + orderId;
	}

	public String refund(String orderId, String reason) {
		System.out.println("退款成功");
		return UUID.randomUUID().toString();
	}

}
