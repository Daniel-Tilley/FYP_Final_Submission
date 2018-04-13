import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {TrainingLogTemplateService} from '../../_services/training-log-template.service';
import {TrainingLogTemplate} from '../../_models/training-log-template.model';
import {TrainingLogTemplatesResponse} from '../../_models/response/training-log-templates-response.model';
import {ErrorResponse} from '../../_models/response/error-response.model';
import {AuthService} from '../../_services/auth.service';

@Component({
  selector: 'app-training-log-template-list',
  templateUrl: './training-log-template-list.component.html',
  styleUrls: ['./training-log-template-list.component.css']
})
export class TrainingLogTemplateListComponent implements OnInit {

  templates: TrainingLogTemplate[] = [];

  constructor(private router: Router, private trainingLogTemplateService: TrainingLogTemplateService, private authService: AuthService) { }

  ngOnInit() {
    this.trainingLogTemplateService.getTemplates(this.authService.getCurrentAuthObject().UserId).subscribe(
      (response: TrainingLogTemplatesResponse) => {
        this.templates = response.Data.Templates;
      },
      (error: ErrorResponse) => {
        console.error(error.error.Message);
      }
    );
  }

  onTemplateClicked(Id: Number) {
    this.router.navigate(['templates', Id]);
  }
}
