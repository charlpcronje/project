package net.integrategroup.ignite.controller.rest;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.integrategroup.ignite.filemanager.FileManager;
import net.integrategroup.ignite.persistence.model.Project;
import net.integrategroup.ignite.persistence.model.ProjectSd;
import net.integrategroup.ignite.persistence.model.ProjectSdStage;
import net.integrategroup.ignite.persistence.model.ProjectStage;
import net.integrategroup.ignite.persistence.model.VPPTree;
import net.integrategroup.ignite.persistence.model.VProject;
import net.integrategroup.ignite.persistence.model.VProjectList;
import net.integrategroup.ignite.persistence.model.VProjectParticipantSdRoles;
import net.integrategroup.ignite.persistence.model.VProjectSd;
import net.integrategroup.ignite.persistence.model.VProjectSdStage;
import net.integrategroup.ignite.persistence.model.VProjectTree;
import net.integrategroup.ignite.persistence.model.VServiceDiscipline;
import net.integrategroup.ignite.persistence.service.DatabaseService;
import net.integrategroup.ignite.persistence.service.IndividualService;
import net.integrategroup.ignite.persistence.service.ProjectSdService;
import net.integrategroup.ignite.persistence.service.ProjectSdStageService;
import net.integrategroup.ignite.persistence.service.ProjectService;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.MapUtils;
import net.integrategroup.ignite.utils.SecurityUtils;

@RestController
@RequestMapping("/rest/ignite/v1/project")
public class ProjectController {

	@Autowired
	DatabaseService databaseService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectSdService projectSdService;

	@Autowired
	IndividualService individualService;

	@Autowired
	ProjectSdStageService projectSdStageService;

	@Autowired
	SecurityUtils securityUtils;

	@Value("${" + IgniteConstants.PROPERTIES_PROJECTS_BASE_PATH + "}")
	private String projectBasePath;

	@GetMapping("/list")
	public ResponseEntity<?> getProjectList(ModelMap modelMap) {
		try {
			List<VProjectList> result = projectService.findAllProjectsInView();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@GetMapping("/list-all-non-sus-projects")
//	public ResponseEntity<?> findNonSustenanceProjectsInView(ModelMap modelMap) {
//		try {
//			List<VProjectList> result = projectService.findNonSustenanceProjectsInView();
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	@GetMapping("/list-only-sustenance-projects")
//	public ResponseEntity<?> findOnlySustenanceProjectsInView(ModelMap modelMap) {
//		try {
//			List<VProjectList> result = projectService.findOnlySustenanceProjectsInView();
//			return ResponseEntity.ok(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@GetMapping({"/{projectId}" })
	public ResponseEntity<?> getProject(ModelMap modelMap,
			@PathVariable(name = "projectId") Long projectId) {
		try {
			VProject result = projectService.findByProjectId(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/next-project-number/{participantIdHost}")
	public ResponseEntity<?> getNextProjectNumberBigInt(ModelMap modelMap,
			@PathVariable(name = "participantIdHost", required = false) Long participantIdHost) {
		try {

			Long result = projectService.getNextProjectNumberBigInt(participantIdHost);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/current-project-stage/{projectId}" )
	public ResponseEntity<?> getCurrentProjectStage(ModelMap modelMap,
			@PathVariable(name = "projectId", required = false) Long projectId) {

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		String currentStage = "";

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!!)

			String exampleSql = "CALL ig_db.getProjectCurrentStage(" + projectId + ", currentStage);";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n");

			String sql = "CALL ig_db.getProjectCurrentStage(?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				
				cstm.setLong(1, projectId);
				cstm.registerOutParameter(2, java.sql.Types.VARCHAR);
				cstm.execute();
				currentStage = cstm.getString(2);
				System.out.println("currentStage " + currentStage);

			return ResponseEntity.ok(currentStage);
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {							//**//
			try {							//**//
				if (cstm != null) {			//**//
					cstm.close();			//**//
				}							//**//
											//**//
				if (conn != null) {			//**//
					conn.close();			//**//
				}							//**//
			} catch (SQLException e) {		//**//
				e.printStackTrace();		//**//
			}								//**//
		}									//**//
	}										//**//	
	//**//


	@GetMapping("/sd/list/{projectId}")
	public ResponseEntity<?> getProjectSds(ModelMap modelMap, @PathVariable(name = "projectId") Long projectId) {
		try {
			List<VProjectSd> result = projectService.findProjectSds(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd/not-selected/{projectId}")
	public ResponseEntity<?> getProjectSdsNotSelected(ModelMap modelMap, @PathVariable(name = "projectId") Long projectId) {
		try {
			List<VServiceDiscipline> result = projectService.getProjectSdsNotSelected(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd/list-not-linked/{projectId}/{projectParticipantId}")
	public ResponseEntity<?> getProjectSds(ModelMap modelMap,
											@PathVariable(name = "projectId") Long projectId,
											@PathVariable(name = "projectParticipantId") Long projectParticipantId) {
		try {
			List<VProjectSd> result = projectService.findProjectSdsNotSelectedForParticipant(projectId, projectParticipantId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-stages/{projectId}")
	public ResponseEntity<?> findProjectStages(ModelMap modelMap, @PathVariable(name = "projectId") Long projectId) {
		try {
			List<ProjectStage> result = projectService.findProjectStages(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/project-stages-remaining/{projectId}/{projectSdId}")
	public ResponseEntity<?> findProjectStagesRemaining(ModelMap modelMap, @PathVariable(name = "projectId") Long projectId
																, @PathVariable(name = "projectSdId") Long projectSdId) {
		try {
			List<ProjectStage> result = projectService.findProjectStagesRemaining(projectId, projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/tree")
	public ResponseEntity<?> getProjectTree(ModelMap modelMap) {
		try {
			List<VProjectTree> result = projectService.getTree();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/behalfOf/{participantIdOnBehalfOf}/{projectId}")
	public ResponseEntity<?> findProjectByBehalfOfId(@PathVariable("participantIdOnBehalfOf") Long participantIdOnBehalfOf, @PathVariable("projectId") Long projectId, ModelMap modelMap) {
		try {
			List<Project> result = projectService.findProjectByBehalfOfId(participantIdOnBehalfOf, projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

//	@PostMapping()
//	public ResponseEntity<?> saveProject(@RequestBody Project project) {
//		try {
//			project = projectService.save(project);
//			VProjectList projectListView = projectService.findProjectListViewByProjectId(project.getProjectId());
//
//			return ResponseEntity.ok(projectListView);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//
//	@PostMapping("/save-project")
//	public ResponseEntity<?> saveNewProject(@RequestBody Map<String, Object> data)
//	{
//		Long projectId = null;
//		Project project = null;
//		VProjectList projectListView = null;
//
//		try {
//			// Convert data into a JSON string (note: there will be numeric fields that must
//			// not be quoted!)
//			ObjectMapper om = new ObjectMapper();
//			String jsonString = om.writeValueAsString(data);
//
//			String exampleSql = "CALL ig_db.saveProject('" + jsonString + "', '"
//					+ securityUtils.getUsername()
//					+ "', projectId"
//					+ "');";
//			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging
//
//			String sql = "CALL ig_db.saveProject(?, ?, ?);";
//
//			try (CallableStatement cstm = databaseService.prepareCall(sql)) { // try in a try to tell java controller
//				// (Tomcat server) when we are finished,
//				// and it can release the memory. NB!
//				// Called: Try with resources.
//				// ALWAYS do this when calling stored
//				// procs!
//				// Whenever we get data from somewhere,
//				// the memory needs to be released. But,
//				// SpringBoot does this automatically
//				// for us by us using Repositories that
//				// extends CRUD repository... Ingrid
//				// nota.
//
//				cstm.setLong(1, jsonString);
//				cstm.setString(2, securityUtils.getUsername());
//				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
//
//				cstm.execute();
//
//				projectId = cstm.getLong(3);
//
//			}
//
//			if (projectId != null) {
//				project = projectService.findByProjectId(projectId);
//				projectListView = projectService.findProjectListViewByProjectId(projectId);
//			}
//
//			return ResponseEntity.ok(projectListView);
//			// return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping("/save-parent-and-sub-project")
	public ResponseEntity<?> saveParentAndSubProject(@RequestBody Map<String, Object> data) throws JsonProcessingException {
		Connection conn = null;
		CallableStatement cstm = null;

		Long projectIdParent = null;
		Long subProjectId = null;
		Project parentProject = null;
		VProject subProject = null;
		VProjectList subProjectListView = null;
		String jsonString = null;

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveProjectParentAndSubProject('" 
					+ jsonString + "', '"
					+ securityUtils.getUsername()
					+ "', projectIdParent"
					+ ", subProjectId"
					+ ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveProjectParentAndSubProject(?, ?, ?, ?);";
			
			try {
				conn = databaseService.getConnection();
				cstm = conn.prepareCall(sql);
				
				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);

				cstm.execute();

				projectIdParent = cstm.getLong(3);
				subProjectId = cstm.getLong(4);
	
				if (subProjectId != null) {
					subProject = projectService.findByProjectId(subProjectId);
				}
	
				return ResponseEntity.ok(subProject);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {
			try {
				if (cstm != null) {
					cstm.close();
				}
				
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String getProjectDirectory(Long projectId) throws Exception {
		String result = projectBasePath;

		if (projectBasePath == null) {
			throw new Exception("The project base path is not configured.");
		}

		if (!projectBasePath.endsWith("/")) {
			projectBasePath += "/";
		}

		// TODO: Should we check that this folder exists?
		// TODO: if not - should we create it?
		// TODO: this must change - we should use the projectNumber and not the ProjectId
		// so that we can still browse the directories in Windows and make sense of
		// it...

		VProjectTree project = projectService.findProjectViewByProjectId(projectId);
		// NOTE: Project Number might have spaces, don't know if this is 100% right...
		// maybe we need a file prefix on projects or types?

		// Todo: Uncomment next few *** lines to fix error. Tony
		String dir = Long.toString(project.getProjectNumberBigInt());

		if (project.getProjectIdParent() != null) {
			while (project != null) {
				project = projectService.findProjectViewByProjectId(project.getProjectIdParent());

				if (project != null) {
					dir = Long.toString(project.getProjectNumberBigInt()) + "/" + dir;
				}
			}
		}
		// Up to here.

		return projectBasePath + dir;
		// return "Please fix this in ProjectController.java";
	}

	@GetMapping("/file-manager/{projectId}")
	public ResponseEntity<?> getFileManager(@PathVariable("projectId") Long projectId, HttpServletRequest request) {
		try {
			Object result = null;

			String dirParam = request.getParameter("id");
			String action = request.getParameter("action");

			String projectDirectory = getProjectDirectory(projectId);
			String directory = projectDirectory;

			if ((dirParam != null) && (!dirParam.isEmpty())) {
				directory = dirParam;
			}

			FileManager fm = new FileManager(projectDirectory);

			if (action == null) {
				result = fm.getDiskEntities(directory);
			} else {
				if ("download".equalsIgnoreCase(action)) {
					File f = new File(directory);
					byte[] documentBody = fm.getFileAsByteArray(directory);

					HttpHeaders responseHeaders = new HttpHeaders();
					responseHeaders.set("charset", "utf-8");
					responseHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					responseHeaders.setContentLength(documentBody.length);
					responseHeaders.set("Content-disposition", "attachment; filename=" + f.getName());

					return new ResponseEntity<>(documentBody, responseHeaders, HttpStatus.OK);
				} else {
					result = fm.executeAction(action, directory, request);
				}
			}

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PostMapping("/delete")
	public ResponseEntity<?> deleteProject(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectId = mu.getAsLongOrNull(data, "projectId");
		String sql = "CALL ig_db.deleteProject(?);";

		System.out.println ("CALL ig_db.deleteProject(" + projectId + ");");
		try {	//**//					
			Object[] params = {		
				projectId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

	@PostMapping("/sd")
	public ResponseEntity<?> saveProjectSd(@RequestBody ProjectSd projectSd) {
		try {

			ProjectSd test = projectSdService.findByProjectSdId(projectSd.getProjectSdId());
			if (test == null) {
				throw new Exception("Project Service Discipline not found");
			}

			projectSd = projectSdService.save(projectSd);
			return ResponseEntity.ok(projectSd);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/project-stage/new")
	public ResponseEntity<?> saveProjectSdStageNew(@RequestBody ProjectSdStage projectSdStage) {
		try {

			ProjectSdStage test = projectSdStageService.findByProjectStageId(projectSdStage.getProjectSdStageId());
			if (test != null) {
				throw new Exception("Project Service Discipline already exists");
			}

			projectSdStage = projectSdStageService.save(projectSdStage);
			return ResponseEntity.ok(projectSdStage);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete-sd")
	public ResponseEntity<?> deleteProjectSd(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long projectSdId = mu.getAsLongOrNull(data, "projectSdId");
		String sql = "CALL ig_db.deleteProjectSd(?);";

		System.out.println ("CALL ig_db.deleteProjectSd(" + projectSdId + ");");
		try {	//**//					
			Object[] params = {		
				projectSdId	
			};						
			
			databaseService.executeStoredProc(sql, params);
			return ResponseEntity.ok().build();
			//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}


	@GetMapping("/pp-tree/{projectId}")
	public ResponseEntity<?> getPPTree(ModelMap modelMap, @PathVariable(name = "projectId") Long projectId) {
		try {
			List<VPPTree> result = projectService.getPPTree(projectId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/pp-tree/children/{id}")
	public ResponseEntity<?> getChildren(@PathVariable("id") Long projectParticipantIdAboveMe) {
		try {
			List<VPPTree> result = projectService.getPPTreeChildren(projectParticipantIdAboveMe);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd/assigned-resources/{projectSdId}")
	public ResponseEntity<?> getProjectSdAssignedResources(ModelMap modelMap, @PathVariable(name = "projectSdId") Long projectSdId) {
		try {
			List<VProjectParticipantSdRoles> result = projectService.getProjectSdAssignedResources(projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd/project-stages/{projectSdId}")
	public ResponseEntity<?> getProjectSdStages(ModelMap modelMap, @PathVariable(name = "projectSdId") Long projectSdId) {
		try {
			List<VProjectSdStage> result = projectService.getProjectSdStages(projectSdId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/sd/project-stages-remaining/{projectSdId}/{projectParticipantSdRoleId}")
	public ResponseEntity<?> getProjectSdStagesRemaining(ModelMap modelMap, @PathVariable(name = "projectSdId") Long projectSdId
																			, @PathVariable(name = "projectParticipantSdRoleId") Long projectParticipantSdRoleId) {
		try {
			List<VProjectSdStage> result = projectService.getProjectSdStagesRemaining(projectSdId, projectParticipantSdRoleId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}




	@GetMapping("/project-sds/{projectStageId}")
	public ResponseEntity<?> getProjectSdsForStageId(ModelMap modelMap, @PathVariable(name = "projectStageId") Long projectStageId) {
		try {
			List<VProjectSdStage> result = projectService.getProjectSdsForStageId(projectStageId);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@PostMapping("/clone-project")
	public ResponseEntity<?> cloneProject(@RequestBody Map<String, Object> data) throws JsonProcessingException
	{

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectIdParent = null;
		Long projectIdCloned = null;
		VProject subProject = null;
		String userName = securityUtils.getUsername();
		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.cloneParentAndOrSubProject('" + jsonString + "', '"
					+ userName
					+ "', projectIdParent"
					+ ", projectIdCloned"
					+ ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.cloneParentAndOrSubProject(?, ?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				

				cstm.setString(1, jsonString);
				cstm.setString(2, userName);
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);
				cstm.registerOutParameter(4, java.sql.Types.BIGINT);

				cstm.execute();

				projectIdParent = cstm.getLong(3);
				projectIdCloned = cstm.getLong(4);
		
				if (projectIdCloned != null) {
					subProject = projectService.findByProjectId(projectIdCloned);
				}
	
				return ResponseEntity.ok(subProject);
				//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {							//**//
			try {							//**//
				if (cstm != null) {			//**//
					cstm.close();			//**//
				}							//**//
											//**//
				if (conn != null) {			//**//
					conn.close();			//**//
				}							//**//
			} catch (SQLException e) {		//**//
				e.printStackTrace();		//**//
			}								//**//
		}									//**//
	}										//**//	
	//**//


	@PostMapping("/sd/new")
	public ResponseEntity<?> saveProjectSdNew(@RequestBody Map<String, Object> data) throws JsonProcessingException
	{

		Connection conn = null;			//**//
		CallableStatement cstm = null;	//**//

		Long projectSdId = null;

		try {
			// Convert data into a JSON string (note: there will be numeric fields that must
			// not be quoted!)
			ObjectMapper om = new ObjectMapper();
			String jsonString = om.writeValueAsString(data);

			String exampleSql = "CALL ig_db.saveProjectSdNew('" + jsonString + "', '"
					+ securityUtils.getUsername()
					+ "', projectSdId"
					+ ");";

			System.out.println("\n\n\n" + exampleSql + "\n\n\n"); // Prints out, but does not execute, for debugging

			String sql = "CALL ig_db.saveProjectSdNew(?, ?, ?);";

			try {										//**//
				conn = databaseService.getConnection(); //**//
				cstm = conn.prepareCall(sql);			//**//
				

				cstm.setString(1, jsonString);
				cstm.setString(2, securityUtils.getUsername());
				cstm.registerOutParameter(3, java.sql.Types.BIGINT);

				cstm.execute();

				projectSdId= cstm.getLong(3);

				return ResponseEntity.ok(projectSdId);
	//**//
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		} finally {							
			try {							
				if (cstm != null) {			
					cstm.close();			
				}							
											
				if (conn != null) {			
					conn.close();			
				}							
			} catch (SQLException e) {		
				e.printStackTrace();		
			}								
		}									
	}											
	//**//



	@GetMapping("/list-only-my-sds-and-roles/{projectParticipantId}")
	public ResponseEntity<?> getPPSdAndRole(ModelMap modelMap,
			@PathVariable(name = "projectParticipantId", required = false) Long projectParticipantId) {
		try {

			List<VProjectParticipantSdRoles> result = projectService.getPPSdAndRole(projectParticipantId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


	@GetMapping("/list-only-my-projects/{participantId}")
	public ResponseEntity<?> findOnlyMyProjects(ModelMap modelMap,
			@PathVariable(name = "participantId", required = false) Long participantId) {
		try {

			List<VProjectList> result = projectService.findOnlyMyProjects(participantId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	@GetMapping("/list-only-my-projects-sustenance/{participantId}")
	public ResponseEntity<?> findOnlyMyProjectsSustenance(ModelMap modelMap,
			@PathVariable(name = "participantId", required = false) Long participantId) {
		try {

			List<VProjectList> result = projectService.findOnlyMyProjectsSustenance(participantId);

			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}		
	

	@GetMapping("/all-non-sustenance-projects")
	public ResponseEntity<?> findAllProjects(ModelMap modelMap) {
		try {
			List<VProjectList> result = projectService.findAllProjects();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
	
	@GetMapping("/all-sustenance-projects")
	public ResponseEntity<?> findAllProjectsSustenance(ModelMap modelMap) {
		try {
			List<VProjectList> result = projectService.findAllProjectsSustenance();
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}		

	

	
}

