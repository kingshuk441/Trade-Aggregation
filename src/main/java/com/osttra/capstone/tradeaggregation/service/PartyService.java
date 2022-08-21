package com.osttra.capstone.tradeaggregation.service;

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Party;

public interface PartyService {
	public CustomResponse<Party> getParties();

	public CustomResponse<Party> getParty(int id);
}
