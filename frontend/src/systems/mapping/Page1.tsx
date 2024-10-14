import { API } from "../../main";

const Page1 = () => {
    API.createAppointment({ date: "2024-01-21", queueNo: 1, time: "21:36", patientId: 1 });

    return <>Page 1</>
}

export default Page1;