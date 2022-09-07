package com.osttra.capstone.tradeaggregation;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.osttra.capstone.tradeaggregation.repository.CancelRepository;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.repository.TradeRepository;
import com.osttra.capstone.tradeaggregation.service.TradeService;

@SpringBootTest
class TradeaggregationApplicationTests {
	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private CancelRepository cancelRepository;

	@Mock
	private PartyRepository partyRepository;

	@Autowired
	private TradeService tradeService;
//
//	@Test
//	void contextLoads() {
//		TradeBody tradeBody = new TradeBody("#a1", "HDFCM", "SBID", LocalDate.now(), LocalDate.now(), "BOND", 1000,
//				LocalDate.now(), "INR", "HDFCM", "SBID");
//
//		TradeBuilder tradeBuilder = new TradeBuilder(tradeBody);
//		Trade newTrade = tradeBuilder.getTrade();
//		when(this.tradeRepository.save(any(Trade.class))).thenReturn(newTrade);
//
//		Party p = new Party("HDFCM", "Hdfcas");
//		p.setInstitution(new Institution("dd"));
//		when(this.partyRepository.findByPartyName("HDFCM")).thenReturn(p);
//		System.out.println("DDDDDDDD:" + this.tradeService.addTrade(tradeBody));
////		assertEquals(1, this.tradeService.addTrade(tradeBody).getData().size());
//
//	}

}
