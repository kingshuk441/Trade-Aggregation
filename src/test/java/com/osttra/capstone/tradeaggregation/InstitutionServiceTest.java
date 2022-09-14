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
import com.osttra.capstone.tradeaggregation.responsebody.InstitutionBody;
import com.osttra.capstone.tradeaggregation.service.InstitutionServiceImpl;
import com.osttra.capstone.tradeaggregation.service.PartyServiceImpl;
import com.osttra.capstone.tradeaggregation.service.TradeServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InstitutionServiceTest {
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
	@DisplayName("find all Institutions")
	public void Get_AllInstitutions_success() {
		when(this.institutionrepository.findAll()).thenReturn(Arrays.asList(I_1, I_2, I_3));
		CustomResponse<Institution> act = this.institutionservice.getInstitutions();
		List<Institution> list = act.getData();
		boolean res = list.size() == 3;
		assertTrue(res);
	}

	@Test
	@DisplayName("find Institution by id")
	public void Get_InstitutionById_success() {
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		CustomResponse<Institution> act = this.institutionservice.getInstitution(10);
		List<Institution> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getInstitutionId() == 10;
		assertTrue(res);
	}

	@Test
	@DisplayName("find all parties of institution")
	public void Get_Allparties_success() {
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		CustomResponse<Party> act = this.institutionservice.getParties(10);
		List<Party> list = act.getData();
		boolean res = list.size() == 4;
		assertTrue(res);
	}

	@Test
	@DisplayName("save a new institution")
	public void save_institution_success() {
		Institution newInstitution = new Institution(20, "Test");
		newInstitution.addParty(PARTY_1);
		newInstitution.addParty(PARTY_2);
		when(this.institutionrepository.findByInstitutionName("Test")).thenReturn(null);
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		when(this.partyrepository.findById(2)).thenReturn(Optional.of(PARTY_2));
		when(this.institutionrepository.save(any(Institution.class))).thenReturn(newInstitution);
		InstitutionBody body = new InstitutionBody(20, "Test", new int[] { 1, 2 });
		CustomResponse<Institution> act = this.institutionservice.addInstitution(body);
		List<Institution> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getAllParties().size() == 2
				&& list.get(0).getInstitutionName().equals(newInstitution.getInstitutionName());
		assertTrue(res);
	}

	@Test
	@DisplayName("add new Party to institution")
	public void save_addPartyInInstitution_success() {
		Party PARTY_100 = new Party(100, "PNBF", "PNB FARIDABAD");
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		when(this.partyrepository.findById(100)).thenReturn(Optional.of(PARTY_100));
		when(this.institutionrepository.save(any(Institution.class))).thenReturn(I_1);
		CustomResponse<Institution> act = this.institutionservice.addParty(10, 100);
		List<Institution> list = act.getData();
		boolean res = list.size() == 1 && list.get(0).getAllParties().size() == 5;
		assertTrue(res);
	}

	@Test
	@DisplayName("get institution by name")
	public void Get_InstitutionByName_success() {
		when(this.institutionrepository.findByInstitutionName("PNBR")).thenReturn(I_1);
		CustomResponse<Institution> act = this.institutionservice.getInstitutionByName("PNBR");
		List<Institution> list = act.getData();
		boolean res = list.get(0).getAllParties().size() == 4
				&& list.get(0).getAllParties().get(0).getPartyName().equals("PNBR");
		assertTrue(res);
	}

	@Test
	@DisplayName("update institution")
	public void update_Institution_success() {
		when(this.partyrepository.findById(1)).thenReturn(Optional.of(PARTY_1));
		when(this.partyrepository.findById(2)).thenReturn(Optional.of(PARTY_2));
		when(this.partyrepository.findById(3)).thenReturn(Optional.of(PARTY_3));
		when(this.partyrepository.findById(8)).thenReturn(Optional.of(PARTY_8));
		when(this.partyrepository.findById(6)).thenReturn(Optional.of(PARTY_6));
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		when(this.institutionrepository.save(any(Institution.class))).thenReturn(I_1);
		InstitutionBody institutionBody = new InstitutionBody(10, "CHANGED", new int[] { 6 });

		CustomResponse<Institution> act = this.institutionservice.updateInstitution(10, institutionBody);
		List<Institution> list = act.getData();
		boolean res = list.get(0).getAllParties().size() == 5 && list.get(0).getInstitutionName().equals("CHANGED");
		assertTrue(res);
	}

	@Test
	@DisplayName("delete a institution")
	public void Delete_Institution_success() {
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		this.institutionservice.deleteInstitution(10);
		Mockito.verify(this.institutionrepository, times(1)).deleteById(10);
	}

	@Test
	@DisplayName("delete a party from institution")
	public void Delete_PartyFromInstitution_success() {
		when(this.institutionrepository.findById(10)).thenReturn(Optional.of(I_1));
		when(this.institutionrepository.save(any(Institution.class))).thenReturn(I_1);
		CustomResponse<Institution> act = this.institutionservice.removeParty(10, 1);
		List<Institution> list = act.getData();
		boolean res = list.get(0).getAllParties().size() == 3;
		assertTrue(res);
	}
}