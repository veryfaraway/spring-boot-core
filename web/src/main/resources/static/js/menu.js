// 메뉴 관련 JavaScript 기능 구현
document.addEventListener('DOMContentLoaded', function() {
    // 메뉴 권한 체크 및 UI 업데이트
    function checkMenuAccess() {
        const menuItems = document.querySelectorAll('.main-menu li');

        menuItems.forEach(item => {
            const menuId = item.dataset.menuId;
            if (menuId) {
                fetch(`/api/menu-permissions/${menuId}/access`)
                    .then(response => response.json())
                    .then(accessType => {
                        if (accessType === 'NO_ACCESS') {
                            item.style.display = 'none';
                        } else if (accessType === 'READ_ONLY_ACCESS') {
                            const editButtons = item.querySelectorAll('.btn-edit');
                            editButtons.forEach(btn => btn.style.display = 'none');
                        }
                    })
                    .catch(error => console.error('메뉴 권한 체크 실패:', error));
            }
        });
    }

    // 메뉴 캐시 관리
    function clearMenuCache() {
        localStorage.removeItem('menuCache');
        location.reload();
    }

    // 활성 메뉴 표시
    function highlightActiveMenu() {
        const currentPath = window.location.pathname;
        const menuLinks = document.querySelectorAll('.main-menu a');

        menuLinks.forEach(link => {
            if (link.getAttribute('href') === currentPath) {
                link.classList.add('active');
                // 부모 메뉴도 활성화
                const parentLi = link.closest('li.has-submenu');
                if (parentLi) {
                    parentLi.classList.add('active');
                }
            }
        });
    }

    // 이벤트 리스너 등록
    document.addEventListener('userLogout', clearMenuCache);

    // 초기화 함수 호출
    checkMenuAccess();
    highlightActiveMenu();
});