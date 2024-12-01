package com.example.web.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.jpa.entity.Menu;
import com.example.core.jpa.repository.MenuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 초기 메뉴 데이터를 생성하는 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MenuDataInitializer implements CommandLineRunner {
	private final MenuRepository menuRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// 기존 메뉴가 없을 경우에만 초기 데이터 생성
		if (menuRepository.count() == 0) {
			log.info("Initializing menu data...");
			try {
/*				// 1. 최상위 메뉴 생성
				Menu homeMenu = createMenu("HOME", "menu.home", "/", 1, null);
				menuRepository.save(homeMenu);
				// log.info("Created home menu: {}", homeMenu.getCode());

				Menu userMenu = createMenu("USER", "menu.user", "#", 2, null);
				menuRepository.save(userMenu);
				// log.info("Created user menu: {}", userMenu.getCode());

				// 2. 사용자 관리 하위 메뉴 생성
				Menu userListMenu = createMenu("USER_LIST", "menu.user.list", "/users", 1, userMenu);
				Menu userCreateMenu = createMenu("USER_CREATE", "menu.user.create", "/users/create", 2, userMenu);

				menuRepository.save(userListMenu);
				menuRepository.save(userCreateMenu);
				log.info("Created user sub-menus");
	*/		} catch (Exception e) {
				log.error("Error initializing menu data", e);
				throw e;
			}
		} else {
			log.info("Menu data already exists. Skipping initialization.");
		}
	}

	/**
	 * 메뉴 엔티티를 생성하는 헬퍼 메소드
	 * @param code 메뉴 코드
	 * @param nameKey 메시지 키
	 * @param url 메뉴 URL
	 * @param sortOrder 정렬 순서
	 * @param parent 상위 메뉴 (없으면 null)
	 * @return 생성된 메뉴 엔티티
	 */
	private Menu createMenu(String code, String nameKey, String url, int sortOrder, Menu parent) {
		Menu menu = new Menu();
/*
		menu.setCode(code);
		menu.setEnabled(true);
		menu.setSortOrder(sortOrder);
		menu.setParent(parent);
		menu.setDepth(parent == null ? 1 : parent.getDepth() + 1);

		// 한국어 메뉴명
		MenuI18n koMenu = new MenuI18n();
		koMenu.setMenu(menu);
		koMenu.setLocale("ko");
		koMenu.setName(nameKey);
		menu.getI18ns().add(koMenu);

		// 영어 메뉴명
		MenuI18n enMenu = new MenuI18n();
		enMenu.setMenu(menu);
		enMenu.setLocale("en");
		enMenu.setName(nameKey);
		menu.getI18ns().add(enMenu);
*/

		return menu;
	}
}
