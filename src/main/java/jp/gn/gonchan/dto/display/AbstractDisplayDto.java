package jp.gn.gonchan.dto.display;

import jp.gn.gonchan.dto.NavigationHeaderDto;
import jp.gn.gonchan.dto.SidebarDto;

public abstract class AbstractDisplayDto {

    private NavigationHeaderDto navDto;

    private SidebarDto sidebarDto;

    public NavigationHeaderDto getNavDto() {
        return navDto;
    }

    public void setNavDto(NavigationHeaderDto navDto) {
        this.navDto = navDto;
    }

    public SidebarDto getSidebarDto() {
        return sidebarDto;
    }

    public void setSidebarDto(SidebarDto sidebarDto) {
        this.sidebarDto = sidebarDto;
    }

    @Override
    public abstract String toString();
}
