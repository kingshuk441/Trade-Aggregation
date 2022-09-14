package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
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
import com.osttra.capstone.tradeaggregation.service.CancelTradeServiceImpl;
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

	@InjectMocks
	CancelTradeServiceImpl cancelTradeService;

	Trade TRADE_1 = new Trade(1, "#T1", "PNBR", "PNB RAIPUR", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
			LocalDate.now(), "BOND", 5000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 1, "UF", null, 10);

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

	Trade newTrade2 = new Trade(5, "#T5", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
			LocalDate.now(), "STOCK", 3000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 0, "UF", null, 10);

	Trade newTrade1 = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
			LocalDate.now(), "STOCK", 3000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 0, "UF", null, 10);

	@AfterEach
	void cleanup() {
		this.allTrades = new ArrayList<>(Arrays.asList(TRADE_1));
		this.allCancelTrades = new ArrayList<>();
	}

	private Trade doAggregation(Trade t1, Trade t2) {
		Trade aggregatedTrade = new Trade(6, "#T6", t1.getPartyName(), t1.getPartyFullName(), t1.getCounterPartyName(),
				t1.getCounterPartyFullName(), LocalDate.now(),
				LocalDate.now(), t1.getInstrumentId(), t1.getNotionalAmount() + t2.getNotionalAmount(), LocalDate.now(),
				t1.getCurrency(), t1.getSeller(), t1.getBuyer(), new Date(), new Date(),
				new Date(), 1, "AGG", null, t1.getInstitutionId());
		CancelTrade cancelTrade1 = new CancelTrade(1, 3000, t1.getCreationTimeStamp(),
				t1.getVersionTimeStamp(), t1.getConfirmationTimeStamp(), aggregatedTrade,
				t1.getTradeRefNum());

		CancelTrade cancelTrade2 = new CancelTrade(2, 5000, t2.getCreationTimeStamp(),
				t2.getVersionTimeStamp(), t2.getConfirmationTimeStamp(), aggregatedTrade,
				t2.getTradeRefNum());
		aggregatedTrade.addCancelTrades(cancelTrade1);
		aggregatedTrade.addCancelTrades(cancelTrade2);
		this.allTrades.remove(this.allTrades.size() - 1);
		this.allTrades.add(aggregatedTrade);
		this.allCancelTrades.add(cancelTrade2);
		this.allCancelTrades.add(cancelTrade1);
		return aggregatedTrade;
	}

	private void addNewTrade(Trade t1) {
		this.allTrades.add(t1);
	}

	@Test
	@DisplayName("find all cancel Trades")
	public void Get_CancelTrades_success() {
		this.addNewTrade(newTrade1);
		this.doAggregation(newTrade1, newTrade2);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<CancelTrade> res = this.cancelTradeService.getCancelTrades();
		List<CancelTrade> list = res.getData();
		assertTrue(list.size() == 2);
	}

	@Test
	@DisplayName("find cancel Trade by id")
	public void Get_CancelTrade_success() {
		this.addNewTrade(newTrade1);
		this.doAggregation(newTrade1, newTrade2);
		when(this.cancelRepository.findById(2)).thenReturn(Optional.of(allCancelTrades.get(0)));
		CustomResponse<CancelTrade> res = this.cancelTradeService.getCancelTrade(2);
		List<CancelTrade> list = res.getData();
		assertTrue(list.size() == 1 && list.get(0).getCancelId() == 2);
	}

	@Test
	@DisplayName("find cancel Trade by invalid id")
	public void Get_CancelTrade_failure() {
		this.addNewTrade(newTrade1);
		this.doAggregation(newTrade1, newTrade2);
		when(this.cancelRepository.findById(100)).thenReturn(Optional.empty());
		try {
			this.cancelTradeService.getCancelTrade(100);
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find aggregated Trade of cancel trade by id")
	public void Get_AggTrade_success() {
		this.addNewTrade(newTrade1);
		Trade aggTrade = this.doAggregation(newTrade1, newTrade2);
		when(this.cancelRepository.findById(2)).thenReturn(Optional.of(allCancelTrades.get(0)));
		CustomResponse<Trade> res = this.cancelTradeService.getAggregatedTrade(2);
		List<Trade> list = res.getData();
		assertTrue(list.size() == 1 && list.get(0).getTradeId() == aggTrade.getTradeId());
	}

}
