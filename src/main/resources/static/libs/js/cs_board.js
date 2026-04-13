function load(pageview, division) {
    new Vue({
            el: '#'+pageview,
            data: {
                boardList: [],
                currentPage: 1,
                totalPages: 0,
                pageSize: 10,
                totalElements: 0,
                setPage: 5,
                searchParams: {
                    division: division,
                    title: 'subject',  // 기본값
                    search: '',       // 검색어
                    searchType: 'title'  // 또는 PHP에서 사용하던 titles 파라미터와 매핑
                },
            },
            created() {
                this.parseQueryString();
                this.loadData();
            },
            methods: {
                    parseQueryString() {
                        const queryString = window.location.search;
                        const urlParams = new URLSearchParams(queryString);

                        if (urlParams.has('title')) {
                            this.searchParams.title = urlParams.get('title');
                        }

                        if (urlParams.has('search')) {
                             this.searchParams.search = urlParams.get('search');
                        }
                    },
                    // PHP의 ListLayer()와 동일한 기능
                    toggleBoard: function(index) {
                        // 한 번에 하나만 열리게 하려면
                        var self = this;
                        this.boardList.forEach(function(item, i) {
                            if (i === index) {
                                // Vue.set으로 반응성 유지
                                if (item.isOpen === undefined) {
                                    self.$set(item, 'isOpen', true);
                                } else {
                                    item.isOpen = !item.isOpen;
                                }
                            } else {
                                if (item.isOpen !== undefined) {
                                    item.isOpen = false;
                                }
                            }
                        });
                    },
                    // API 호출로 이벤트 목록 로드
                    async loadData(page = 1) {
                         try {
                            const response = await axios.get('/api/board/faq', {
                                params: {
                                     ...this.searchParams,
                                    page: page,
                                    size: this.pageSize
                                }
                            });
                             console.log(response.data.content);
                            this.boardList = response.data.content || [];
                            this.currentPage = response.data.currentPage || 1;
                            this.totalPages = response.data.totalPages || 0;
                            this.totalElements = response.data.totalElements || 0;

                         } catch (error) {
                            console.error('Data 목록 로드 실패:', error);
                            this.boardList = [];
                            alert('데이터를 불러오는데 실패했습니다.');
                         }
                    },
                     // 페이지 변경
                    changePage(page) {
                        if (page < 1 || page > this.totalPages || page === this.currentPage) {
                            return;
                        }
                        this.loadData(page);
                        // 페이지 상단으로 스크롤
                        window.scrollTo({ top: 0, behavior: 'smooth' });
                    },
                    getPageNumbers() {
                        const pageGroupSize = 10; // PHP의 bbs_page_scale
                        let pageStart = Math.floor(this.currentPage / pageGroupSize) * pageGroupSize + 1;

                        if ((this.currentPage % pageGroupSize) === 0) {
                            pageStart -= pageGroupSize;
                        }

                        const pageEnd = Math.min(pageStart + pageGroupSize - 1, this.totalPages);
                        const pages = [];

                        for (let i = pageStart; i <= pageEnd; i++) {
                            pages.push(i);
                        }

                        return pages;
                    },
            },

    });
}