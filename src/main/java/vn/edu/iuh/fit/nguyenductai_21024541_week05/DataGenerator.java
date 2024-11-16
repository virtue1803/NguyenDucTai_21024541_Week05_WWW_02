package vn.edu.iuh.fit.nguyenductai_21024541_week05;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.*;

import java.time.LocalDate;
import java.util.*;

@Component
public class DataGenerator implements CommandLineRunner {
    private final AddressRepository addressRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final CandidateRepository candidateRepository;
    private final CandidateSkillRepository candidateSkillRepository;
    private final JobRepository jobRepository;
    private final JobSkillRepository jobSkillRepository;
    private final ExperienceRepository experienceRepository;

    public DataGenerator(AddressRepository addressRepository,
                         CompanyRepository companyRepository,
                         SkillRepository skillRepository,
                         CandidateRepository candidateRepository,
                         CandidateSkillRepository candidateSkillRepository,
                         JobRepository jobRepository,
                         JobSkillRepository jobSkillRepository,
                         ExperienceRepository experienceRepository) {
        this.addressRepository = addressRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
        this.candidateRepository = candidateRepository;
        this.candidateSkillRepository = candidateSkillRepository;
        this.jobRepository = jobRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.experienceRepository = experienceRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // 1. Generate Addresses
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            addresses.add(new Address(
                    null,
                    faker.address().streetName(),
                    faker.address().city(),
                    (short) random.nextInt(200), // Generate a random country code (0-199)
                    faker.address().streetAddressNumber(),
                    faker.address().zipCode()
            ));
        }
        addressRepository.saveAll(addresses);

        // 2. Generate Companies
        Set<String> uniqueEmails = new HashSet<>();
        while (uniqueEmails.size() < 50) {
            uniqueEmails.add(faker.internet().emailAddress());
        }

        List<Company> companies = new ArrayList<>();
        for (String email : uniqueEmails) {
            companies.add(new Company(
                    null,
                    faker.lorem().paragraph(),
                    email,
                    faker.company().name() + "_" + random.nextInt(1000),
                    faker.phoneNumber().phoneNumber(),
                    faker.internet().url(),
                    addresses.get(random.nextInt(addresses.size()))
            ));
        }
        companyRepository.saveAll(companies);

        // 3. Generate Skills
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            skills.add(new Skill(
                    null,
                    faker.lorem().sentence(),
                    faker.job().keySkills(),
                    SkillType.values()[random.nextInt(SkillType.values().length)]
            ));
        }
        skillRepository.saveAll(skills);

        // 4. Generate Candidates
        List<Candidate> candidates = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            candidates.add(new Candidate(
                    null,
                    LocalDate.now().minusYears(random.nextInt(40) + 20),
                    faker.internet().emailAddress(),
                    faker.name().fullName(),
                    faker.phoneNumber().cellPhone(),
                    faker.internet().password(),
                    addresses.get(random.nextInt(addresses.size())),
                    CandidateRole.USER,
                    true
            ));
        }
        candidateRepository.saveAll(candidates);

        // 5. Generate Candidate Skills
        List<CandidateSkill> candidateSkills = new ArrayList<>();
        for (Candidate candidate : candidates) {
            for (int i = 0; i < random.nextInt(3) + 1; i++) {
                candidateSkills.add(new CandidateSkill(
                        new CandidateSkillId(candidate, skills.get(random.nextInt(skills.size()))),
                        faker.lorem().sentence(),
                        SkillLevel.values()[random.nextInt(SkillLevel.values().length)]
                ));
            }
        }
        candidateSkillRepository.saveAll(candidateSkills);

        // 6. Generate Jobs
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            jobs.add(new Job(
                    null,
                    faker.lorem().paragraph(),
                    faker.job().title(),
                    companies.get(random.nextInt(companies.size()))
            ));
        }
        jobRepository.saveAll(jobs);

        // 7. Generate Job Skills
        List<JobSkill> jobSkills = new ArrayList<>();
        for (Job job : jobs) {
            for (int i = 0; i < random.nextInt(3) + 1; i++) {
                jobSkills.add(new JobSkill(
                        new JobSkillId(job, skills.get(random.nextInt(skills.size()))),
                        faker.lorem().sentence(),
                        SkillLevel.values()[random.nextInt(SkillLevel.values().length)]
                ));
            }
        }
        jobSkillRepository.saveAll(jobSkills);

        // 8. Generate Experiences
        List<Experience> experiences = new ArrayList<>();
        for (Candidate candidate : candidates) {
            for (int i = 0; i < random.nextInt(3) + 1; i++) {
                LocalDate fromDate = LocalDate.now().minusYears(random.nextInt(10) + 1);
                LocalDate toDate = fromDate.plusYears(random.nextInt(3) + 1);
                experiences.add(new Experience(
                        faker.company().name(),
                        faker.lorem().sentence(),
                        faker.job().title(),
                        fromDate,
                        toDate,
                        candidate
                ));
            }
        }
        experienceRepository.saveAll(experiences);

        System.out.println("Data generation completed successfully!");
    }
}
