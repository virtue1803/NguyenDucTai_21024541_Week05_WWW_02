//package vn.edu.iuh.fit.nguyenductai_21024541_week05;
//
//import net.datafaker.Faker;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.CandidateRole;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillLevel;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.enums.SkillType;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.CandidateSkillId;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.ids.JobSkillId;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.*;
//import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories.*;
//
//import java.time.LocalDate;
//import java.util.*;
//
//@Component
//public class DataGenerator implements CommandLineRunner {
//    private final AddressRepository addressRepository;
//    private final CompanyRepository companyRepository;
//    private final SkillRepository skillRepository;
//    private final CandidateRepository candidateRepository;
//    private final CandidateSkillRepository candidateSkillRepository;
//    private final JobRepository jobRepository;
//    private final JobSkillRepository jobSkillRepository;
//    private final ExperienceRepository experienceRepository;
//
//    public DataGenerator(AddressRepository addressRepository,
//                         CompanyRepository companyRepository,
//                         SkillRepository skillRepository,
//                         CandidateRepository candidateRepository,
//                         CandidateSkillRepository candidateSkillRepository,
//                         JobRepository jobRepository,
//                         JobSkillRepository jobSkillRepository,
//                         ExperienceRepository experienceRepository) {
//        this.addressRepository = addressRepository;
//        this.companyRepository = companyRepository;
//        this.skillRepository = skillRepository;
//        this.candidateRepository = candidateRepository;
//        this.candidateSkillRepository = candidateSkillRepository;
//        this.jobRepository = jobRepository;
//        this.jobSkillRepository = jobSkillRepository;
//        this.experienceRepository = experienceRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Faker faker = new Faker();
//        Random random = new Random();
//
//        // Clear existing data
//        experienceRepository.deleteAll();
//        candidateSkillRepository.deleteAll();
//        jobSkillRepository.deleteAll();
//        jobRepository.deleteAll();
//        candidateRepository.deleteAll();
//        companyRepository.deleteAll();
//        addressRepository.deleteAll();
//        System.out.println("Old data cleared!");
//
//        // 1. Generate Addresses
//        List<Address> addresses = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            addresses.add(new Address(
//                    null,
//                    faker.address().streetName(),
//                    faker.address().city(),
//                    faker.address().country(),
//                    faker.address().streetAddressNumber(),
//                    faker.address().zipCode()
//            ));
//        }
//        addressRepository.saveAll(addresses);
//        System.out.println("Addresses saved!");
//
//        // 2. Generate Companies
//        List<Company> companies = new ArrayList<>();
//        for (int i = 0; i < addresses.size(); i++) {
//            companies.add(new Company(
//                    null,
//                    faker.lorem().paragraph(),
//                    faker.internet().emailAddress(),
//                    faker.company().name(),
//                    faker.phoneNumber().phoneNumber(),
//                    faker.internet().url(),
//                    addresses.get(i) // Use each address once
//            ));
//        }
//        companyRepository.saveAll(companies);
//        System.out.println("Companies saved!");
//
//        // 3. Generate Skills
//        List<Skill> skills = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            skills.add(new Skill(
//                    null,
//                    faker.lorem().sentence(),
//                    faker.job().keySkills(),
//                    SkillType.values()[random.nextInt(SkillType.values().length)]
//            ));
//        }
//        skillRepository.saveAll(skills);
//        System.out.println("Skills saved!");
//
//        // 4. Generate Candidates
//        Set<String> uniqueEmails = new HashSet<>();
//        Set<Long> usedAddressIds = new HashSet<>();
//
//        while (uniqueEmails.size() < 200) {
//            uniqueEmails.add(faker.internet().emailAddress());
//        }
//
//        List<Candidate> candidates = new ArrayList<>();
//        Iterator<String> emailIterator = uniqueEmails.iterator();
//
//        for (int i = 0; i < 100; i++) {
//            Address address;
//            do {
//                address = addresses.get(random.nextInt(addresses.size()));
//            } while (usedAddressIds.contains(address.getId())); // Use address id to ensure uniqueness
//
//            usedAddressIds.add(address.getId());
//
//            candidates.add(new Candidate(
//                    null,
//                    LocalDate.now().minusYears(random.nextInt(40) + 20),
//                    emailIterator.next(),
//                    faker.name().fullName(),
//                    faker.phoneNumber().cellPhone(),
//                    faker.internet().password(),
//                    address,
//                    CandidateRole.USER,
//                    true
//            ));
//        }
//        candidateRepository.saveAll(candidates);
//        System.out.println("Candidates saved!");
//
//        // 5. Generate Candidate Skills
//        List<CandidateSkill> candidateSkills = new ArrayList<>();
//        for (Candidate candidate : candidates) {
//            for (int i = 0; i < random.nextInt(3) + 1; i++) {
//                candidateSkills.add(new CandidateSkill(
//                        new CandidateSkillId(candidate, skills.get(random.nextInt(skills.size()))),
//                        faker.lorem().sentence(),
//                        SkillLevel.values()[random.nextInt(SkillLevel.values().length)]
//                ));
//            }
//        }
//        candidateSkillRepository.saveAll(candidateSkills);
//        System.out.println("Candidate skills saved!");
//
//        // 6. Generate Jobs
//        List<Job> jobs = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            jobs.add(new Job(
//                    null,
//                    faker.lorem().paragraph(),
//                    faker.job().title(),
//                    companies.get(random.nextInt(companies.size()))
//            ));
//        }
//        jobRepository.saveAll(jobs);
//        System.out.println("Jobs saved!");
//
//        // 7. Generate Job Skills
//        List<JobSkill> jobSkills = new ArrayList<>();
//        for (Job job : jobs) {
//            for (int i = 0; i < random.nextInt(3) + 1; i++) {
//                jobSkills.add(new JobSkill(
//                        new JobSkillId(job, skills.get(random.nextInt(skills.size()))),
//                        faker.lorem().sentence(),
//                        SkillLevel.values()[random.nextInt(SkillLevel.values().length)]
//                ));
//            }
//        }
//        jobSkillRepository.saveAll(jobSkills);
//        System.out.println("Job skills saved!");
//
//        // 8. Generate Experiences
//        List<Experience> experiences = new ArrayList<>();
//        for (Candidate candidate : candidates) {
//            for (int i = 0; i < random.nextInt(3) + 1; i++) {
//                LocalDate fromDate = LocalDate.now().minusYears(random.nextInt(10) + 1);
//                LocalDate toDate = fromDate.plusYears(random.nextInt(3) + 1);
//                experiences.add(new Experience(
//                        faker.company().name(),
//                        faker.lorem().sentence(),
//                        faker.job().title(),
//                        fromDate,
//                        toDate,
//                        candidate
//                ));
//            }
//        }
//        experienceRepository.saveAll(experiences);
//        System.out.println("Experiences saved!");
//
//        System.out.println("Data generation completed successfully!");
//    }
//}
