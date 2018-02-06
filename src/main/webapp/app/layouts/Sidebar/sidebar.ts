import {Component} from '@angular/core';
import {VERSION} from '../../app.constants';
import {Router} from '@angular/router';

@Component({
    templateUrl: './sidebar.html',
    styleUrls: [
        'style4.css'
    ]
})

export class SidebarComponent {
    version: string;
    constructor(
        private router: Router
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
    }
}
