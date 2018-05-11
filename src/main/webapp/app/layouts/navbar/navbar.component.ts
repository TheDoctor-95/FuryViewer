import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';

import { ProfileService } from '../profiles/profile.service';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from '../../shared';

import { VERSION } from '../../app.constants';

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: [
        'navbar.css'
    ]
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;
    lloc: string;
    constructor(
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        public router: Router,

    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
        router.events.subscribe((val) => {
            // see also
            console.log(val.toString())
        });
    }

    ngOnInit() {
        this.languageHelper.getAll().then((languages) => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().subscribe((profileInfo) => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });
        console.log(this.router.url);
        this.filtro();
    }

    filtro() {
        switch (this.router.url) {
            case '/user-ext': {
                this.lloc = 'Profile';
                break;
            }
            case '/': {
                this.lloc = 'Home';
                break;
            }
            case '/search': {
                this.lloc = 'Search';
                break;
            }
            case '/search': {
                this.lloc = 'Search';
                break;
            }
            case '/calendar': {
                this.lloc = 'Calendar';
                break;
            }
            case '/watchlist': {
                this.lloc = 'Watchlist';
                break;
            }
            case '/about-us': {
                this.lloc = 'About Us';
                break;
            }
            default: {
                this.lloc = '';
                break;
            }
        }
    }

    changeLanguage(languageKey: string) {
      this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
