package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

	Trade newTrade2 = new Trade(5, "#T5", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
			LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 0, "UF", 10);

	Trade newTrade1 = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
			LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
			new Date(), 0, "UF", 10);

	@AfterEach
	void cleanup() {
		this.allTrades = new ArrayList<>(Arrays.asList(TRADE_1, TRADE_2, TRADE_3));
		this.allCancelTrades = new ArrayList<>();
	}

	@Test
	@DisplayName("find all trades")
	public void Get_AllTrades_success() {
		when(this.tradeRepository.findAll()).thenReturn(Arrays.asList(TRADE_1, TRADE_2, TRADE_3));
		CustomResponse<Trade> act = this.tradeService.getTrades();
		List<Trade> list = act.getData();
		boolean res = list.size() == 3;
		assertTrue(res);
	}

	@Test
	@DisplayName("find trade by id")
	public void Get_TradeById_success() {
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(TRADE_1));
		CustomResponse<Trade> act = this.tradeService.getTrade(1);
		List<Trade> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getTradeId() == 1;
		assertTrue(res);
	}

	@Test
	@DisplayName("find trades by party")
	public void Get_PartyName_success() {
		when(this.tradeRepository.findByPartyName("PNBR")).thenReturn(Arrays.asList(TRADE_1));
		CustomResponse<Trade> act = this.tradeService.findByPartyName("PNBR");
		List<Trade> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getPartyName().equals("PNBR");
		assertTrue(res);
	}

	@Test
	@DisplayName("find trades by institution")
	public void Get_Institution_success() {
		when(this.institutionRepository.findByInstitutionName("PNB")).thenReturn(I_1);
		when(this.tradeRepository.findByInstitutionId(10)).thenReturn(Arrays.asList(TRADE_1, TRADE_3));
		CustomResponse<Trade> act = this.tradeService.findByInstitutionName("PNB");
		List<Trade> list = act.getData();
		boolean res = list.size() == 2;
		assertTrue(res);
	}

	private Trade doAggregation(Trade t1, Trade t2) {
		Trade aggregatedTrade = new Trade(6, "#T6", t1.getPartyName(), t1.getPartyFullName(), t1.getCounterPartyName(),
				t1.getCounterPartyFullName(), LocalDate.now(),
				LocalDate.now(), t1.getInstrumentId(), t1.getNotionalAmount() + t2.getNotionalAmount(), LocalDate.now(),
				t1.getCurrency(), t1.getSeller(), t1.getBuyer(), new Date(), new Date(),
				new Date(), 1, "AGG", t1.getInstitutionId());
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
	@DisplayName("Get Cancel Trades for Aggr trades")
	public void Get_cancelTrades_success()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		boolean res = this.allCancelTrades.size() == 0;
		this.addNewTrade(newTrade1);
		Trade aggregatedTrade = this.doAggregation(newTrade1, newTrade2);
		Field isFirstFetch = this.tradeService.getClass().getDeclaredField("isFirstFetch");
		isFirstFetch.setAccessible(true);
		isFirstFetch.setBoolean(this.tradeService, false);
		when(this.tradeRepository.findById(6)).thenReturn(Optional.of(aggregatedTrade));
		CustomResponse<CancelTrade> act = this.tradeService.getCancelTrades(6);
		List<CancelTrade> list = act.getData();
		res = res && list.size() == 2
				&& list.get(0).getAggregatedTrade().getTradeRefNum().equals("#T6")
				&& list.get(1).getAggregatedTrade().getTradeRefNum().equals("#T6");
		assertTrue(res);
	}

	@Test
	@DisplayName("find trade by TRN party unconfirm")
	public void Get_findByTrnParty_Unconfirm_success() {
		this.addNewTrade(newTrade1);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<Trade> act = this.tradeService.findByTrnParty("#T4", "PNBD");
		List<Trade> list = act.getData();
		boolean res = list.get(0).getTradeRefNum().equals("#T4") && list.get(0).getPartyName().equals("PNBD")
				&& list.get(0).getStatus().equals("UF");
		assertTrue(res);
	}

	@Test
	@DisplayName("find trade by TRN party aggr")
	public void Get_findByTrnParty_Aggr_success()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		this.addNewTrade(newTrade1);
		Trade aggregatedTrade = this.doAggregation(newTrade1, newTrade2);
		Field isFirstFetch = this.tradeService.getClass().getDeclaredField("isFirstFetch");
		isFirstFetch.setAccessible(true);
		isFirstFetch.setBoolean(this.tradeService, false);

		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<Trade> act = this.tradeService.findByTrnParty("#T5", "PNBD");
		List<Trade> list = act.getData();
		boolean res = list.get(0).getTradeRefNum().equals("#T6") && list.get(0).getPartyName().equals("PNBD")
				&& list.get(0).getStatus().equals("AGG");
		assertTrue(res);
	}

	@Test
	@DisplayName("find trades by TRN status UNCONFIRM")
	public void Get_findByPartyStatus_Unconf_success() {
		Trade newTrade = new Trade(5, "#T5", "PNBL", "PNB LADAKH", "SBITN", "SBI TAMIL NADU", LocalDate.now(),
				LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);
		when(this.tradeRepository.save(any(Trade.class))).thenReturn(newTrade);
		when(this.partyRepository.findByPartyName("PNBL")).thenReturn(PARTY_2);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		TradeBody tradeBody = new TradeBody("#T5", "PNBL", "SBITN", LocalDate.now(), LocalDate.now(), "STOCK", 3000,
				LocalDate.now(), "INR", "SELLER", "BUYER");
		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		actRes.getData();
		CustomResponse<Trade> act = this.tradeService.findByPartyStatus("PNBL", "unconfirm");
		List<Trade> list = act.getData();
		boolean res = list.size() == 2;
		assertTrue(res);
	}

	@Test
	@DisplayName("find aggr trade by TRN status CANCEL")
	public void Get_findByPartyStatus_cancel_success()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		this.addNewTrade(newTrade1);
		Trade aggregatedTrade = this.doAggregation(newTrade1, newTrade2);
		Field isFirstFetch = this.tradeService.getClass().getDeclaredField("isFirstFetch");
		isFirstFetch.setAccessible(true);
		isFirstFetch.setBoolean(this.tradeService, false);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<Trade> act = this.tradeService.findByPartyStatus("PNBD", "cancel");
		List<Trade> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getTradeRefNum().equals("#T6");
		assertTrue(res);
	}

	@Test
	@DisplayName("Adding Unconfirm Trade")
	public void save_UnConfirmTrade_success() {
		Trade newTrade = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 3000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);
		when(this.tradeRepository.save(any(Trade.class))).thenReturn(newTrade);
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

	@Test
	@DisplayName("Adding aggregated Trade")
	public void save_AggrTrade_success() {
		this.addNewTrade(newTrade1);

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
				"#T4");
		aggregatedTrade.addCancelTrades(cancelTrade1);
		aggregatedTrade.addCancelTrades(cancelTrade2);

		when(this.tradeRepository.save(any(Trade.class))).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		TradeBody tradeBody = new TradeBody("#T5", "PNBD", "ICICIK", LocalDate.now(), LocalDate.now(), "BOND", 5000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();

		boolean res = aggregatedTrade.getNotionalAmount() == trade.get(0).getNotionalAmount()
				&& aggregatedTrade.getStatus().equals(trade.get(0).getStatus())
				&& aggregatedTrade.getAggregatedFrom().size() == trade.get(0).getAggregatedFrom().size()
				&& this.allTrades.size() == 4;

		assertTrue(res);
	}

	@Test
	@DisplayName("Matching condition for Trade")
	public void save_AggrTrade_failure() {
		this.addNewTrade(newTrade1);
		Trade newTrade2 = new Trade(5, "#T5", "PNBR", "PNB RAIPUR", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "STOCK", 5000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);

		when(this.tradeRepository.save(any(Trade.class))).thenReturn(newTrade2);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeBody tradeBody = new TradeBody("#T5", "PNBR", "ICICIK", LocalDate.now(), LocalDate.now(), "BOND", 5000,
				LocalDate.now(), "INR", "HDFCM", "SBID");

		CustomResponse<Trade> actRes = this.tradeService.addTrade(tradeBody);
		List<Trade> trade = actRes.getData();
		this.allTrades.add(trade.get(0));

		assertTrue(trade.get(0).getAggregatedFrom() == null && trade.get(0).getStatus().equals("UF")
				&& trade.get(0).getTradeRefNum().equals(newTrade2.getTradeRefNum()) && this.allTrades.size() == 5);
	}

	@Test
	@DisplayName("Update unconf Trade (non matching field)")
	public void update_UnconfirmTrades_success() {
		this.addNewTrade(newTrade1);
		LocalDate updatedDate = LocalDate.now();
		Trade updatedTrade = new Trade(4, "#T4", "PNBD", "PNB DELHI", "ICICIK", "ICICI KOLKATA", LocalDate.now(),
				LocalDate.now(), "BOND", 3000, updatedDate, "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "UF", 10);

		Trade prev = this.allTrades.get(this.allTrades.size() - 1);
		when(this.tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findById(4)).thenReturn(Optional.of(prev));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeUpdateBody body = new TradeUpdateBody("#T4", "PNBD", "ICICIK", prev.getTradeDate(),
				prev.getEffectiveDate(), prev.getInstrumentId(), prev.getNotionalAmount(), updatedDate,
				prev.getCurrency(), prev.getSeller(), prev.getBuyer(), prev.getVersionTimeStamp(),
				prev.getConfirmationTimeStamp(), prev.getVersion(), prev.getInstitutionId());
		CustomResponse<Trade> act = this.tradeService.updateTrade(4, body);
		List<Trade> list = act.getData();
		boolean res = list.get(0).getVersion() == 1 && updatedDate.equals(list.get(0).getMaturityDate())
				&& list.get(0).getVersionTimeStamp().compareTo(body.getVersionTimeStamp()) > 0;
		assertTrue(res);
	}

	@Test
	@DisplayName("Update agr Trade")
	public void update_aggrTrades_success() {
		this.addNewTrade(newTrade1);
		Trade aggregatedTrade = this.doAggregation(newTrade1, newTrade2);
		when(this.tradeRepository.save(any(Trade.class))).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.partyRepository.findByPartyName("PNBL")).thenReturn(PARTY_2);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(TRADE_1));
		when(this.tradeRepository.findById(6)).thenReturn(Optional.of(aggregatedTrade));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeUpdateBody body = new TradeUpdateBody("#T4", "PNBL", "ICICIK", TRADE_1.getTradeDate(),
				TRADE_1.getEffectiveDate(), TRADE_1.getInstrumentId(), TRADE_1.getNotionalAmount(),
				TRADE_1.getMaturityDate(), TRADE_1.getCurrency(), TRADE_1.getSeller(), TRADE_1.getBuyer(),
				TRADE_1.getVersionTimeStamp(), TRADE_1.getConfirmationTimeStamp(), TRADE_1.getVersion(),
				TRADE_1.getInstitutionId());

		CustomResponse<Trade> act = this.tradeService.updateTrade(1, body);
		List<Trade> list = act.getData();
		List<CancelTrade> cancelTrades = this.tradeService.getCancelTrades(6).getData();

		boolean res = list.get(0).getVersion() == 1 &&
				list.get(0).getStatus().equals("AGG")
				&& list.get(0).getAggregatedFrom().size() == 2 &&
				list.get(0).getPartyFullName().equals("PNB DELHI")
				&& list.get(0).getCounterPartyFullName().equals("ICICI KOLKATA")
				&& list.get(0).getNotionalAmount() == 6000 && cancelTrades.size() == 2;
		assertTrue(res);
	}

	@Test
	@DisplayName("Delete a unconf Trade")
	public void Delete_UnconfirmTrade_success() {
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(TRADE_1));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		this.tradeService.deleteTrade(1);

		Mockito.verify(this.tradeRepository, times(1)).deleteById(1);
	}

	@Test
	@DisplayName("Matching fields")
	public void matchingFields() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		java.lang.reflect.Method method = TradeServiceImpl.class.getDeclaredMethod("matchingFields", Trade.class);
		method.setAccessible(true);
		Trade matchedTrade = (Trade) method.invoke(this.tradeService, TRADE_1);
		assertTrue(matchedTrade == TRADE_1);
	}

	@Test
	@DisplayName("Aggregation Logic")
	public void AggregationLogic() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		Trade newTrade1 = new Trade(5, "#T5", "PNBR", "PNB RAIPUR", "SBITN", "SBITN TAMIL NADU", LocalDate.now(),
				LocalDate.now(), "STOCK", 1000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 0, "UF", 10);

		Trade aggregatedTrade = new Trade(6, "#T6", "PNBR", "PNB RAIPUR", "SBITN", "SBITN TAMIL NADU", LocalDate.now(),
				LocalDate.now(), "STOCK", 6000, LocalDate.now(), "DLR", "SELLER", "BUYER", new Date(), new Date(),
				new Date(), 1, "AGG", 10);
		CancelTrade cancelTrade1 = new CancelTrade(1, 5000, newTrade1.getCreationTimeStamp(),
				newTrade1.getVersionTimeStamp(), newTrade1.getConfirmationTimeStamp(), aggregatedTrade,
				newTrade1.getTradeRefNum());

		CancelTrade cancelTrade2 = new CancelTrade(2, 1000, newTrade1.getCreationTimeStamp(),
				newTrade1.getVersionTimeStamp(), newTrade1.getConfirmationTimeStamp(), aggregatedTrade,
				newTrade1.getTradeRefNum());
		aggregatedTrade.addCancelTrades(cancelTrade1);
		aggregatedTrade.addCancelTrades(cancelTrade2);
		when(this.tradeRepository.save(any(Trade.class))).thenReturn(aggregatedTrade);
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		java.lang.reflect.Method method = TradeServiceImpl.class.getDeclaredMethod("aggregationTrade", Trade.class,
				Trade.class);
		method.setAccessible(true);
		Trade aggTrade = (Trade) method.invoke(this.tradeService, TRADE_1, newTrade1);
		assertTrue(aggTrade.getAggregatedFrom().size() == 2
				&& aggTrade.getNotionalAmount() == aggTrade.getNotionalAmount());
	}

	@Test
	@DisplayName("all aggregated Trades")
	public void Get_AggregatedTrades()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		this.addNewTrade(newTrade1);
		this.doAggregation(newTrade1, newTrade2);
		Field isFirstFetch = this.tradeService.getClass().getDeclaredField("isFirstFetch");
		isFirstFetch.setAccessible(true);
		isFirstFetch.setBoolean(this.tradeService, false);
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<Trade> act = this.tradeService.getAllAggregatedTrades();
		List<Trade> list = act.getData();
		assertTrue(list.size() == 1 && allCancelTrades.size() == 2);
	}

	@Test
	@DisplayName("all unconfirmed Trades")
	public void Get_UnconfirmedTrades() {
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		CustomResponse<Trade> act = this.tradeService.getAllUnconfirmedTrades();
		List<Trade> list = act.getData();
		assertTrue(list.size() == 3 && allCancelTrades.size() == 0);
	}

}
