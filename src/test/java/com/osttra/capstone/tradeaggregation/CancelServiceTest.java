package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.repository.CancelRepository;
import com.osttra.capstone.tradeaggregation.repository.InstitutionRepository;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.repository.TradeRepository;
import com.osttra.capstone.tradeaggregation.responsebody.TradeBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionServiceImpl;
import com.osttra.capstone.tradeaggregation.service.PartyServiceImpl;
import com.osttra.capstone.tradeaggregation.service.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CancelServiceTest {

	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private PartyRepository partyRepository;

	@Mock
	private InstitutionRepository institutionRepository;

	@Mock
	private CancelRepository cancelRepository;

	@InjectMocks
	TradeServiceImpl tradeService;

	@InjectMocks
	PartyServiceImpl partyService;

	@InjectMocks
	InstitutionServiceImpl institutionService;

	Trade TRADE_1 = new Trade(1, "#T1", "PNBR", "PNB RAIPUR", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
			LocalDate.now(), "BOND", 5000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 1, "UF", 10);

	Party PARTY_1 = new Party(1, "PNBR", "PNB RAIPUR");
	Party PARTY_2 = new Party(2, "PNBL", "PNB LADAKH");
	Party PARTY_3 = new Party(3, "PNBR", "PNB DELHI");
	Party PARTY_4 = new Party(4, "SBITN", "SBI TAMIL NADU");
	Party PARTY_5 = new Party(5, "SBIGT", "SBI GUJARAT");
	Party PARTY_6 = new Party(6, "ICICIK", "ICICI KOLKATA");
	Party PARTY_7 = new Party(7, "HDFCG", "HDFCG");
	Party PARTY_8 = new Party(8, "PNBD", "PNB DELHI");

	Institution I_1 = new Institution(10, "PNB");
	Institution I_2 = new Institution(11, "ICICI");
	Institution I_3 = new Institution(12, "SBI");
	Institution I_4 = new Institution(13, "HDFC");

	private ArrayList<Trade> allTrades = new ArrayList<>();

	private ArrayList<CancelTrade> allCancelTrades = new ArrayList<>();

	@BeforeEach()
	public void setup() {
		PARTY_1.setInstitution(I_1);
		PARTY_2.setInstitution(I_1);
		PARTY_3.setInstitution(I_1);
		PARTY_4.setInstitution(I_3);
		PARTY_5.setInstitution(I_3);
		PARTY_6.setInstitution(I_2);
		PARTY_7.setInstitution(I_4);
		PARTY_8.setInstitution(I_1);

		I_1.addParty(PARTY_1);
		I_1.addParty(PARTY_2);
		I_1.addParty(PARTY_3);
		I_1.addParty(PARTY_8);

		I_2.addParty(PARTY_6);

		I_3.addParty(PARTY_4);
		I_3.addParty(PARTY_5);

		I_4.addParty(PARTY_7);

		this.allTrades = new ArrayList<>(Arrays.asList(TRADE_1));
		this.allCancelTrades = new ArrayList<>();
	}

	@Test
	@DisplayName("Adding aggregated Trade")
	public void save_AggrTrade_success() {

		Trade newTrade1 = new Trade(5, "#T5", "PNBR", "PNB RAIPUR", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
				LocalDate.now(), "BOND", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);

		Trade aggregatedTrade = new Trade(6, "#T6", "PNBR", "PNB RAIPUR", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
				LocalDate.now(), "BOND", 8000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "AGG", 10);

		CancelTrade cancelTrade1 = new CancelTrade(1, 3000, TRADE_1.getCreationTimeStamp(),
				TRADE_1.getVersionTimeStamp(), TRADE_1.getConfirmationTimeStamp(), aggregatedTrade,
				TRADE_1.getTradeRefNum());

		CancelTrade cancelTrade2 = new CancelTrade(2, 5000, newTrade1.getCreationTimeStamp(),
				newTrade1.getVersionTimeStamp(), newTrade1.getConfirmationTimeStamp(), aggregatedTrade,
				newTrade1.getTradeRefNum());
		aggregatedTrade.addCancelTrades(cancelTrade1);
		aggregatedTrade.addCancelTrades(cancelTrade2);

		when(this.tradeRepository.save(any(Trade.class))).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.tradeRepository.findAll()).thenReturn(this.allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		TradeBody tradeBody = new TradeBody("#T5", "PNBR", "SBITN", LocalDate.now(), LocalDate.now(), "BOND", 5000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();
		this.allTrades.remove(this.allTrades.size() - 1);
		this.allTrades.add(aggregatedTrade);
		this.allCancelTrades.add(cancelTrade2);
		this.allCancelTrades.add(cancelTrade1);
		boolean res = trade.get(0).getAggregatedFrom().size() == 2
				&& trade.get(0).getAggregatedFrom().get(0).getTradeRefNum().equals("#T1")
				&& trade.get(0).getAggregatedFrom().get(1).getTradeRefNum().equals("#T5");

		assertTrue(res);
	}

}
