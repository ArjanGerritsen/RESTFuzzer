package nl.ou.se.rest.fuzzer.components.shared;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;

public abstract class QueryUtil {

    public static String toLike(String query) {
        if (StringUtils.isAllBlank(query)) {
            return "%";
        } else {
            return String.format("%%%s%%", query);
        }
    }

    public static PageRequest toPageRequest(int curPage, int perPage) {
        return PageRequest.of(curPage - 1, perPage);
    }
}