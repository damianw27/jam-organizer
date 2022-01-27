package pl.wilenskid.jamorganizer.action.project;

import org.apache.commons.collections4.ListUtils;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.wilenskid.jamorganizer.action.BaseAction;
import pl.wilenskid.jamorganizer.entity.bean.UserBean;
import pl.wilenskid.jamorganizer.entity.bean.CommentBean;
import pl.wilenskid.jamorganizer.entity.bean.ProjectBean;
import pl.wilenskid.jamorganizer.entity.User;
import pl.wilenskid.jamorganizer.entity.Project;
import pl.wilenskid.jamorganizer.enums.UserRole;
import pl.wilenskid.jamorganizer.service.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@RequestMapping("/project/view")
public class ProjectViewAction extends BaseAction {
    public static final int COMMENT_PAGE_SIZE = 15;

    private final ProjectService projectService;
    private final UserService userService;
    private final CommentService commentService;
    private final ParameterParseService parameterParseService;
    private final EndorsementService endorsementService;

    public ProjectBean project;
    public List<User> authors;
    public List<Integer> commentPageIndexes;
    public List<CommentBean> comments;
    public List<UserBean> allUsers;
    public Integer commentPagesCount;
    public boolean isLoggedUserEndorseProject = false;

    @Inject
    public ProjectViewAction(ProjectService projectService,
                             UserService userService,
                             CommentService commentService, ParameterParseService parameterParseService, EndorsementService endorsementService) {
        super("project/project-view");
        this.projectService = projectService;
        this.userService = userService;
        this.commentService = commentService;
        this.parameterParseService = parameterParseService;
        this.endorsementService = endorsementService;
    }

    @Override
    public void onLoad(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String, String> pathParams) throws Exception {

        int commentPageIndex = parameterParseService.getInt("commentPageIndex", pathParams);
        long selectedCommentIndex = parameterParseService.getInt("selectedCommentIndex", pathParams, -1);

        if (selectedCommentIndex != -1) {
            commentPageIndex = 0;
        }

        if (commentPageIndex == 0) {
            pathParams.put("commentPageIndex", "0");
        }

        String projectId = pathParams.get("projectId");

        Project projectModel = projectService
            .getById(Long.valueOf(projectId))
            .orElseThrow(ChangeSetPersister.NotFoundException::new);

        project = projectService.toBean(projectModel);
        authors = new ArrayList<>(projectModel.getMembers());

        if (commentPageIndex == -1) {
            List<Long> commentsIds = commentService
                .getCommentsForProject(project.getId())
                .map(CommentBean::getId)
                .collect(Collectors.toList());

            List<List<Long>> partition = ListUtils.partition(commentsIds, COMMENT_PAGE_SIZE);

            int index = 0;

            for (List<Long> idsChunk : partition) {
                if (idsChunk.contains(selectedCommentIndex)) {
                    commentPageIndex = index;
                    break;
                }

                index++;
            }
        }

        comments = commentService
            .getCommentsForProject(project.getId())
            .skip((long) commentPageIndex * COMMENT_PAGE_SIZE)
            .limit(COMMENT_PAGE_SIZE)
            .collect(Collectors.toList());

        long allCommentsCount = commentService
            .getCommentsForProject(project.getId())
            .count();

        commentPagesCount = (int) Math.ceil(allCommentsCount / (double) COMMENT_PAGE_SIZE);

        commentPageIndexes = new ArrayList<>();

        for (int i = 0; i < commentPagesCount; i++) {
            commentPageIndexes.add(i);
        }

        allUsers = userService
            .getAll()
            .stream()
            .map(userService::toBean)
            .collect(Collectors.toList());

        isLoggedUserEndorseProject = endorsementService.canLoggedUserEndorseProject(projectModel);
    }

    @Override
    public List<UserRole> getAllowedRoles() {
        return null;
    }
}
