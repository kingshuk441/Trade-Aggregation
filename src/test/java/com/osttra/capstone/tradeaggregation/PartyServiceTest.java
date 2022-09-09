package com.osttra.capstone.tradeaggregation;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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

import com.osttra.capstone.tradeaggregation.entity.CustomResponse;
import com.osttra.capstone.tradeaggregation.entity.Institution;
import com.osttra.capstone.tradeaggregation.entity.Party;
import com.osttra.capstone.tradeaggregation.repository.InstitutionRepository;
import com.osttra.capstone.tradeaggregation.repository.PartyRepository;
import com.osttra.capstone.tradeaggregation.repository.TradeRepository;
import com.osttra.capstone.tradeaggregation.responsebody.PartyBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionServiceImpl;
import com.osttra.capstone.tradeaggregation.service.PartyServiceImpl;
import com.osttra.capstone.tradeaggregation.service.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PartyServiceTest {

	@Mock
	private TradeRepository traderepository;

	@Mock
	private PartyRepository partyrepository;

	@Mock
	private InstitutionRepository institutionrepository;

	@InjectMocks
	TradeServiceImpl tradeservice;

	@InjectMocks
	PartyServiceImpl partyservice;

	@InjectMocks
	InstitutionServiceImpl institutionservice;

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
	}

	@Test
	@DisplayName("find all Parties")
	public void Get_AllParties_success() {
		when(this.partyrepository.findAll()).thenReturn(Arrays.asList(PARTY_1, PARTY_2, PARTY_3));
		CustomResponse<Party> act = this.partyservice.getParties();
		List<Party> list = act.getData();
		boolean res = list.size() == 3;
		assertTrue(res);
	}

	@Test
	@DisplayName("find parties by id")
	public void Get_partyID_success() {
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		CustomResponse<Party> act = this.partyservice.getParty(1);
		List<Party> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getPartyId() == 1;
		assertTrue(res);
	}

	@Test
	@DisplayName("find institution of party")
	public void Get_institution_success() {
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		CustomResponse<Institution> act = this.partyservice.getInstitution(1);
		List<Institution> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getAllParties().get(0).getPartyId() == 1;
		assertTrue(res);
	}

	@Test
	@DisplayName("find party by party name")
	public void Get_partyByName_success() {
		when(this.partyrepository.findByPartyName("PNBR")).thenReturn(PARTY_1);
		CustomResponse<Party> act = this.partyservice.getPartyByName("PNBR");
		List<Party> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getPartyName().equals(PARTY_1.getPartyName());
		assertTrue(res);
	}

	@Test
	@DisplayName("add new party")
	public void save_party_success() {
		PartyBody partyBody = new PartyBody(9, "PNBF", "PNB FARIDABAD", 10);
		Party newParty = new Party(9, "PNBF", "PNB FARIDABAD");
		newParty.setInstitution(I_1);
		when(this.partyrepository.findByPartyName("PNBF")).thenReturn(null);
		when(this.partyrepository.save(any(Party.class))).thenReturn(newParty);
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));

		CustomResponse<Party> act = this.partyservice.addParty(partyBody);
		List<Party> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getInstitution().getInstitutionId() == 10
				&& list.get(0).getPartyName().equals(newParty.getPartyName());
		assertTrue(res);
	}

	@Test
	@DisplayName("update a party")
	public void update_party_success() {
		PartyBody partyBody = new PartyBody(1, "PNBR", "PNB RAIPUR", 10);
		when(this.partyrepository.findByPartyName("PNBF")).thenReturn(null);
		Party updatedParty = new Party(1, "PNBR", "PNB RAMPUR");
		updatedParty.setInstitution(I_1);
		when(this.partyrepository.save(any(Party.class))).thenReturn(updatedParty);
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));

		CustomResponse<Party> act = this.partyservice.updateParty(1, partyBody);
		List<Party> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getInstitution().getInstitutionId() == 10
				&& list.get(0).getPartyFullName().equals(updatedParty.getPartyFullName());
		assertTrue(res);
	}

	@Test
	@DisplayName("delete a party")
	public void delete_party_success() {
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		this.partyservice.deleteParty(1);
		Mockito.verify(this.partyrepository, times(1)).deleteById(1);
	}

}