package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.osttra.capstone.tradeaggregation.customexception.FoundException;
import com.osttra.capstone.tradeaggregation.customexception.NotFoundException;
import com.osttra.capstone.tradeaggregation.entity.CancelTrade;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.entity.Trade;
import com.osttra.capstone.tradeaggregation.repository.CancelRepository;
import com.osttra.capstone.tradeaggregation.repository.InstitutionRepository;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.repository.TradeRepository;
import com.osttra.capstone.tradeaggregation.responsebody.InstitutionBody;
import com.osttra.capstone.tradeaggregation.responsebody.PartyBody;
import com.osttra.capstone.tradeaggregation.responsebody.TradeUpdateBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionServiceImpl;
import com.osttra.capstone.tradeaggregation.service.PartyServiceImpl;
import com.osttra.capstone.tradeaggregation.service.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceNegativeTest {

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

	@Test
	@DisplayName("update trade same institution")
	public void update_sameInstitution_failure() {
		when(this.partyRepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		when(this.partyRepository.findByPartyName("PNBL")).thenReturn(PARTY_2);
		when(this.partyRepository.findByPartyName("SBITN")).thenReturn(PARTY_4);
		when(this.partyRepository.findByPartyName("PNBD")).thenReturn(PARTY_8);
		when(this.partyRepository.findByPartyName("ICICIK")).thenReturn(PARTY_6);
		when(this.tradeRepository.findById(1)).thenReturn(Optional.of(TRADE_1));
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);

		TradeUpdateBody body = new TradeUpdateBody("#T4", null, "PNBD", TRADE_1.getTradeDate(),
				TRADE_1.getEffectiveDate(), TRADE_1.getInstrumentId(), TRADE_1.getNotionalAmount(),
				TRADE_1.getMaturityDate(), TRADE_1.getCurrency(), TRADE_1.getSeller(), TRADE_1.getBuyer(),
				TRADE_1.getVersionTimeStamp(), TRADE_1.getConfirmationTimeStamp(), TRADE_1.getVersion(),
				TRADE_1.getInstitutionId());

		try {
			this.tradeService.updateTrade(1, body);
		} catch (FoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find all trades of institution")
	public void Get_TradesByInstitution_failure() {
		when(this.institutionRepository.findByInstitutionName("TEST")).thenReturn(null);
		try {
			this.tradeService.findByInstitutionName("TEST");
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find trade by id")
	public void Get_TradesById_failure() {

		when(this.tradeRepository.findById(100)).thenReturn(Optional.empty());
		try {
			System.out.println(this.tradeService.getTrade(100));
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find trade by Trn Party")
	public void Get_TradesByTrnParty_failure() {
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		try {
			this.tradeService.findByTrnParty("#r", "PNBL");
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find trades by Party status")
	public void Get_TradesByPartyStatus_failure() {
		when(this.tradeRepository.findAll()).thenReturn(allTrades);
		when(this.cancelRepository.findAll()).thenReturn(allCancelTrades);
		try {
			this.tradeService.findByPartyStatus("#r", "PNBL");
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	// ----------------------------------------------------------
	@Test
	@DisplayName("find institution by Id")
	public void Get_Institutions_failure() {
		when(this.institutionRepository.findById(100)).thenReturn(Optional.empty());

		try {
			this.institutionService.getInstitution(100);
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("add already added party in institution")
	public void save_addPartyInInstitution_failure() {
		when(this.institutionRepository.findById(10)).thenReturn(Optional.of(I_1));
		when(this.partyRepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		try {
			this.institutionService.addParty(10, 1);
		} catch (FoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("add already added institution")
	public void save_addInstitution_failure() {
		when(this.institutionRepository.findByInstitutionName("PNB")).thenReturn(I_1);
		try {
			InstitutionBody body = new InstitutionBody(20, "PNB", new int[] { 1, 2 });
			this.institutionService.addInstitution(body);
		} catch (FoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	// ------------------------------------------------
	@Test
	@DisplayName("find a not present party")
	public void Get_PartyById_failure() {
		when(this.partyRepository.findById(100)).thenReturn(Optional.empty());
		try {
			this.partyService.getParty(100);
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("find party by unknown name")
	public void Get_partyByName_failure() {
		when(this.partyRepository.findByPartyName("TEST")).thenReturn(null);
		try {
			this.partyService.getPartyByName("TEST");
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}

	@Test
	@DisplayName("add a party with unknown Institution")
	public void save_party_failure() {
		when(this.institutionRepository.findById(100)).thenReturn(Optional.empty());
		try {
			PartyBody partyBody = new PartyBody(9, "PNBF", "PNB FARIDABAD", 100);
			this.partyService.addParty(partyBody);
		} catch (NotFoundException e) {
			assertTrue(true);
		} catch (Exception ex) {
			assertTrue(false);
		}
	}
}
