package UsersList.portlet;

import UsersList.constants.UsersListPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author fmancine
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=UsersList",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + UsersListPortletKeys.USERSLIST,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class UsersListPortlet extends MVCPortlet {
	  private static final int USERS_PER_PAGE = 5;

		private List<User> getUsers() {
		    List<User> users = new ArrayList<>();
		    for (int i = 1; i <= 50; i++) {
		        users.add(new User(i, "user" + i + "@gmail.com", "user" + i, "user" + i, "user" + i));
		    }
		    return users;
		}
		
		@Override
		public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		    int page = ParamUtil.getInteger(renderRequest, "page", 1);
		    String searchName = ParamUtil.getString(renderRequest, "name", "");
		    String searchSurname = ParamUtil.getString(renderRequest, "surname", "");
		    String searchEmail = ParamUtil.getString(renderRequest, "email", "");

		    List<User> allUsers = getUsers(); 
		    List<User> filteredUsers = allUsers.stream()
		        .filter(user -> user.getName().contains(searchName) &&
		                user.getSurname1().contains(searchSurname) &&
		                user.getEmail().contains(searchEmail))
		        .collect(Collectors.toList());

		    int totalItems = filteredUsers.size();
		    int totalPages = (int) Math.ceil((double) totalItems / USERS_PER_PAGE);
		    int start = (page - 1) * USERS_PER_PAGE;
		    int end = Math.min(start + USERS_PER_PAGE, totalItems);

		    List<User> paginatedUsers = filteredUsers.subList(start, end);

		    renderRequest.setAttribute("users", paginatedUsers);
		    renderRequest.setAttribute("totalPages", totalPages);
		    renderRequest.setAttribute("currentPage", page);

		    super.doView(renderRequest, renderResponse);
		}
}