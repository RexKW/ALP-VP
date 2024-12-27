import { ActivityResponse, CreateActivityRequest, toActivityResponse, toActivityResponseList } from "../model/activity-model";
import { Validation } from "../validation/validation";
import { Activity, Schedule_Per_Day , Destination} from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { ActivityValidation } from "../validation/activity-validation";
import { toDestinationResponse, DestinationResponse, toDestinationResponseList } from "../model/destination-model";

export class DestinationService{
    static async getAllProvinces() {
        const axios = require('axios');
        const destinations = await axios.get('https://rexkw.github.io/api-wilayah-indonesia/api/provinces.json')

        return destinations.data

        
    }

    static async getAllCitiesInProvince(provinceId: string){
        const axios = require('axios');
        const response = await axios.get(`https://emsifa.github.io/api-wilayah-indonesia/api/regencies/${provinceId}.json`);
        return response.data
    }

    static async getAllDestination(){
        const provinces = await this.getAllProvinces();
        const allCities = [];
        for (const province of provinces) { 
            const regencies = await this.getAllCitiesInProvince(province.id); 
            const cityWithProvince = regencies.map(({ id ,name }: Destination) =>({
                id,
                name,
                province: province.name
            }));
            allCities.push(...cityWithProvince)
        }

        return allCities
    }

    static async getDestinationbyID(destinationId: string){
        const axios = require('axios');
        const destination = await axios.get(`https://emsifa.github.io/api-wilayah-indonesia/api/regency/${destinationId}.json`);

        return destination
    }

    


    static async getDestinationbyName(name: string): Promise<DestinationResponse[]>{
        const destination = await this.checkDestinationName(name)

        return toDestinationResponseList(destination)
    }



    static async checkDestinationID(
            destination_id: number
        ): Promise<Destination> {
            const destination = await prismaClient.destination.findUnique({
                where: {
                    id: destination_id
                },
            })
    
            if (!destination) {
                throw new ResponseError(400, "Destination not found!")
            }
    
            return destination
        }

    static async checkDestinationName(
        name: string
    ): Promise<Destination[]> {
        
        const destination = await prismaClient.destination.findMany({
            where:{
                name: name
            }
        })

        if (!destination) {
            throw new ResponseError(400, "Destination not found!")
        }

        return destination
    }


    

}