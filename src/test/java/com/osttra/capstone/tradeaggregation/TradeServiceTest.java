package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionServiceImpl;
import com.osttra.capstone.tradeaggregation.service.PartyServiceImpl;
import com.osttra.capstone.tradeaggregation.service.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TradeServiceTest {

	@Mock
	private TradeRepository tradeRepository;

	@Mock
	private CancelRepository cancelRepository;

	@Mock
	private InstitutionRepository institutionRepository;

	@Mock
	private PartyRepository partyRepository;

	@InjectMocks
	private TradeServiceImpl tradeService;

	@InjectMocks
	private PartyServiceImpl partyService;

	@InjectMocks
	private InstitutionServiceImpl institutionService;

	Trade TRADE_1 = new Trade(1, "#T1", "PNBR", "PNB RAIPUR", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
			LocalDate.now(), "BOND", 5000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 1, "UF", 10);

	Trade TRADE_2 = new Trade(2, "#T2", "HDFCG", "HDFC GOA", "SBIGT", "SBI GUJARAT", LocalDate.now(), LocalDate.now(),
			"MF", 2000, LocalDate.now(), "INR", "SELLER", "BUYER", new Date(), new Date(), new Date(), 1, "UF", 13);

	Trade TRADE_3 = new Trade(3, "#T3", "PNBL", "PNB LADAKH", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
			LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
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

	List<Trade> allTrades;
	List<CancelTrade> allCancelTrades;

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

		this.allTrades = new ArrayList<>(Arrays.asList(TRADE_1, TRADE_2, TRADE_3));
		this.allCancelTrades = new ArrayList<>();
	}

//	@Test
	@DisplayName("Adding Unconfirm Trade")
	public void save_UnConfirmTrade_success() {
		Trade newTrade = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);
		when(this.tradeRepository.save(newTrade)).thenReturn(newTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeBody tradeBody = new TradeBody("#T4", "PNBD", "ICICIK", LocalDate.now(), LocalDate.now(), "BOND", 3000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();

		assertTrue(trade != null && trade.get(0).getInstitutionId() == newTrade.getInstitutionId()
				&& trade.get(0).getPartyFullName().equals(newTrade.getPartyFullName()));
		this.allTrades.add(trade.get(0));
	}

//	@Test
	@DisplayName("Adding aggregated Trade")
	public void save_AggrTrade_success() {
		this.save_UnConfirmTrade_success();

		Trade newTrade1 = new Trade(5, "#T5", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);

		Trade aggregatedTrade = new Trade(6, "#T6", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 8000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "AGG", 10);

		CancelTrade cancelTrade1 = new CancelTrade(1, 3000, newTrade1.getCreationTimeStamp(),
				newTrade1.getVersionTimeStamp(), newTrade1.getConfirmationTimeStamp(), aggregatedTrade,
				newTrade1.getTradeRefNum());

		CancelTrade cancelTrade2 = new CancelTrade(2, 5000, newTrade1.getCreationTimeStamp(),
				newTrade1.getVersionTimeStamp(), newTrade1.getConfirmationTimeStamp(), aggregatedTrade,
				newTrade1.getTradeRefNum());
		aggregatedTrade.addCancelTrades(cancelTrade1);
		aggregatedTrade.addCancelTrades(cancelTrade2);

		when(this.tradeRepository.save(aggregatedTrade)).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeBody tradeBody = new TradeBody("#T5", "PNBD", "ICICIK", LocalDate.now(), LocalDate.now(), "BOND", 5000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();
		this.allTrades.remove(this.allTrades.size() - 1);
		this.allTrades.add(aggregatedTrade);
		this.allCancelTrades.add(cancelTrade2);
		this.allCancelTrades.add(cancelTrade1);

		boolean res = aggregatedTrade.getNotionalAmount() == trade.get(0).getNotionalAmount()
				&& aggregatedTrade.getStatus().equals(trade.get(0).getStatus())
				&& aggregatedTrade.getAggregatedFrom().size() == trade.get(0).getAggregatedFrom().size()
				&& this.allTrades.size() == 4;

		assertTrue(res);
	}

//	@Test
	@DisplayName("Matching condition for Trade")
	public void save_AggrTrade_failure() {
		this.save_UnConfirmTrade_success();
		Trade newTrade2 = new Trade(5, "#T5", "PNBR", "PNB RAIPUR", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 5000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);

		when(this.tradeRepository.save(newTrade2)).thenReturn(newTrade2);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeBody tradeBody = new TradeBody("#T5", "PNBR", "ICICIK", LocalDate.now(), LocalDate.now(), "BOND", 5000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();

		assertTrue(trade.get(0).getAggregatedFrom() == null && trade.get(0).getStatus().equals("UF")
				&& trade.get(0).getTradeRefNum().equals(newTrade2.getTradeRefNum()) && this.allTrades.size() == 4);
	}

//	@Test
	@DisplayName("Get Cancel Trades for Aggr trades")
	public void get_cancelTrades_success() {
		boolean res = this.allCancelTrades.size() == 0;
		this.save_AggrTrade_success();
		res = res && this.allCancelTrades.size() == 2
				&& this.allCancelTrades.get(0).getAggregatedTrade().getTradeRefNum().equals("#T6")
				&& this.allCancelTrades.get(1).getAggregatedTrade().getTradeRefNum().equals("#T6");

		assertTrue(res);
	}

	@Test
	@DisplayName("Update unconf Trade")
	public void update_UnconfirmTrades_success() {
		this.save_UnConfirmTrade_success();
		System.out.println(this.allTrades.get(this.allTrades.size() - 1));
		LocalDate updatedDate = LocalDate.now();
		Trade updatedTrade = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "BOND", 3000, updatedDate, "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "UF", 10);

		Trade prev = this.allTrades.get(this.allTrades.size() - 1);
		when(this.tradeRepository.save(updatedTrade)).thenReturn(updatedTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findById(0)).thenReturn(Optional.of(prev));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeUpdateBody body = new TradeUpdateBody("#T4", "PNBD", "ICICIK", prev.getTradeDate(),
				prev.getEffectiveDate(), prev.getInstrumentId(), prev.getNotionalAmount(), updatedDate,
				prev.getCurrency(), prev.getSeller(), prev.getBuyer(), prev.getVersionTimeStamp(),
				prev.getConfirmationTimeStamp(), prev.getVersion(), prev.getInstitutionId());
		CustomResponse<Trade> act = this.tradeService.updateTrade(0, body);
		List<Trade> list = act.getData();

		boolean res = list.get(0).getVersion() == 1 && updatedDate.equals(list.get(0).getMaturityDate())
				&& list.get(0).getVersionTimeStamp().compareTo(body.getVersionTimeStamp()) > 0;
		assertTrue(res);
	}

	@Test
	@DisplayName("Update agr Trade")
	public void update_aggrTrades_success() {

		Trade aggregatedTrade = new Trade(6, "#T6", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 8000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "AGG", 10);

		Trade prev = TRADE_1;

//		when(this.tradeRepository.save(aggregatedTrade)).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("PNBL")).thenReturn(PARTY_2);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(prev));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeUpdateBody body = new TradeUpdateBody("#T4", "PNBL", "ICICIK", prev.getTradeDate(),
				prev.getEffectiveDate(), prev.getInstrumentId(), prev.getNotionalAmount(), prev.getMaturityDate(),
				prev.getCurrency(), prev.getSeller(), prev.getBuyer(), prev.getVersionTimeStamp(),
				prev.getConfirmationTimeStamp(), prev.getVersion(), prev.getInstitutionId());

		CustomResponse<Trade> act = this.tradeService.updateTrade(1, body);
		List<Trade> list = act.getData();
		boolean res = list.get(0).getVersion() == 1 && list.get(0).getStatus().equals("AGG")
				&& list.get(0).getAggregatedFrom().size() == 2 && list.get(0).getNotionalAmount() == 8000;
		assertTrue(res);
	}

	@Test
	public void whenDelete_thenObjectShouldBeDeleted() {
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(TRADE_1));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		CustomResponse<Trade> res = this.tradeService.deleteTrade(1);
		List<Trade> list = res.getData();

		Mockito.verify(this.tradeRepository, times(1)).deleteById(1);

	}

}
