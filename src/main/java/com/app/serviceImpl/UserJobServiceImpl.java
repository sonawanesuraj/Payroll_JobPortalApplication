package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.IListUserJobDto;
import com.app.dto.UserJobDto;
import com.app.entities.JobEntity;
import com.app.entities.RoleEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserJobEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.JobRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserJobRepository;
import com.app.repository.UserRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.UserJobInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserJobServiceImpl implements UserJobInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserJobRepository userJobRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public void candidateApplyJob(UserJobDto userJobDto) throws Exception {
		UserEntity userEntity = userRepository.findById(userJobDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));

		final String url = "job has been applied";

		List<JobEntity> job = jobRepository.findByIdIn(userJobDto.getJobId());
		if (job.size() == userJobDto.getJobId().size()) {

			ArrayList<UserJobEntity> userJobList = new ArrayList<>();

			for (int i = 0; i < job.size(); i++) {
				UserJobEntity userJobEntity = new UserJobEntity();
				userJobEntity.setUser(userEntity);
				userJobEntity.setJob(job.get(i));
				userJobList.add(userJobEntity);
				userJobRepository.saveAll(userJobList);
				String email = userEntity.getEmail();
				this.emailService.sendSimpleMessage(userEntity.getEmail(), "subject", url);
				List<RoleEntity> role = this.roleRepository.findAll();

				String roleName = role.get(1).getRoleName();
				System.out.println("dhjf" + roleName);
				String roleName1 = role.get(2).getRoleName();
				System.out.println("hjf" + roleName1);

				if (roleName.equals("Recruiter")) {
					Long role1 = role.get(1).getId();

					UserRoleEntity userrole = this.userRoleRepository.findById(role1)
							.orElseThrow(() -> new ResourceNotFoundException("not found"));
					String email1 = userrole.getUser().getEmail();

					this.emailService.sendSimpleMessage(email1, roleName1, email);
				}

				else if (roleName1.equals("Recruiter")) {
					Long role1 = role.get(2).getId();

					UserRoleEntity userrole = this.userRoleRepository.findByroleId(role1);
					String emai = userrole.getUser().getEmail();

					this.emailService.sendSimpleMessage(emai, "subject", url);

				}
			}
		} else

		{
			new ResourceNotFoundException("not found");
		}

	}

	@Override
	public Page<IListUserJobDto> candidateAppliedJobList(String search, String pageNumber, String pageSize) {

		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListUserJobDto> iListUserJobDto;
		iListUserJobDto = userJobRepository.findByOrderByIdDesc(paging, IListUserJobDto.class);

		return iListUserJobDto;
	}

}
