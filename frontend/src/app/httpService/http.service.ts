import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';


export interface Pokemon{
    id: number;
    poke_number: number;
    trainer_id: number;
    happiness: number;
    hunger: number;
    fatigue: number;
    xp: number;
}

export interface Trainer{
    id: number;
    username: string;
    pokeList: Array<Pokemon>;
    currency: number;
}



@Injectable({
  providedIn: 'root'
})
export class HttpService {

    trainerApiUrl = "/api/trainer/";
    pokeApiUrl = "/api/pokemon/";

  constructor(private httpClient: HttpClient) { }

    registerRequest(username, email, password): Observable<Trainer>{

        const body = new HttpParams()
            .set('username', username)
            .set('email', email)
            .set('password', password);

        return this.httpClient.post<Trainer>(this.trainerApiUrl+'register',
            body.toString(),
            {
                headers: new HttpHeaders()
                    .set('Content-Type', 'application/x-www-form-urlencoded')
            }
        );
    }

    login(username, password): Observable<Trainer> {

        const body = new HttpParams()
            .set('username', username)
            .set('password', password);

        return this.httpClient.post<Trainer>(this.trainerApiUrl+'login',
            body.toString(),
            {
                headers: new HttpHeaders()
                    .set('Content-Type', 'application/x-www-form-urlencoded')
            }
        );
    }

    fetchTrainer(): Observable<Trainer>{
      return this.httpClient.get<Trainer>(this.trainerApiUrl+"reload")
    }

    logout(): Observable<any>{
      return this.httpClient.delete(this.trainerApiUrl+"logout", {responseType: "text"})
    }
//     newPokemonRequest(data: Pokemon) {
//         this.apiUrl = "/pokemon/new";
//         return this.httpClient.post<Pokemon>(this.apiUrl, data); //this should also prob. be a post
//     }
 }