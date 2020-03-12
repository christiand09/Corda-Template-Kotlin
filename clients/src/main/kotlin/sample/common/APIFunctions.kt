package sample.common

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.NodeInfo
import org.springframework.stereotype.Service
import sample.webserver.NodeRPCConnection

@Service
class APIFunctions(rpcConnection: NodeRPCConnection){

    private val proxy = rpcConnection.proxy

    /** SAMPLE OUTPUT OF NETWORKMAPSNAPSHOT **/
    /**
    addresses: "localhost:10002"
    legalIdentitiesAndCerts: "O=MonetaGo Inc, L=New York, C=US"
    platformVersion: 5
    serial: 1583975002190
     **/

    fun myIdentityParty(): Party{
        return proxy.nodeInfo().legalIdentities.first()
    }

    fun myIdentityNameOnly(): String{
        return proxy.nodeInfo().legalIdentities.first().name.commonName.toString()
        /** Sample Output **/
        /** QuantumCrowd **/
    }

    fun myIdentityName(): String{
        return proxy.nodeInfo().legalIdentities.first().name.toString()
        /** Sample Output **/
        /** O=QuantumCrowd,L=London,C=GB **/
    }

    fun allParties(): List<Party>{
        return proxy.nodeInfo().legalIdentities
    }

    fun listOfNodeInfo(): List<NodeInfo>{
        return proxy.networkMapSnapshot()
    }

    fun stringToPartyAPI(name: String): Party{
        return proxy.wellKnownPartyFromX500Name(CordaX500Name.parse(name))
                ?: throw IllegalArgumentException("No match found for $name")
    }

    fun stringToUniqueIdentifier(id: String): UniqueIdentifier{
        return UniqueIdentifier.fromString(id)
    }

}